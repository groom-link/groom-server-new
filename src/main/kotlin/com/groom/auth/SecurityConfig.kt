package com.groom.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.auth.domain.UserReader
import com.groom.common.ExceptionResponse
import com.groom.common.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.time.Duration
import java.time.Instant


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val userReader: UserReader,
    private val jwtEncoder: JwtEncoder,
    @Value("\${spring.security.jwt.access.expires-in:3600}")
    private val ACCESS_TOKEN_EXPIRES_IN: Long
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { disable() }
        http.cors { disable() }
        http.formLogin { disable() }
        http.oauth2Login {
            it.loginProcessingUrl("/api/v1/oauth2/login/*")
            it.successHandler(oAuth2successLoginHandler)
        }
        http.exceptionHandling {
            it.accessDeniedHandler(accessDeniedHandler)
            it.authenticationEntryPoint(entryPoint)
        }
        return http.build()
    }

    private val oAuth2successLoginHandler = { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            val oauth2User = authentication.details as OAuth2User
            val userId: Long = oauth2User.attributes["user_id"] as Long
            val user = userReader.read(userId)
            sendResponse(response, JwtResponse(user.claims))
        }
    private val entryPoint = { _: HttpServletRequest, response: HttpServletResponse, e: RuntimeException ->
        response.status = 401
        sendResponse(response, ExceptionResponse.of(HttpStatus.UNAUTHORIZED.value(), e))
    }

    private val accessDeniedHandler = { _: HttpServletRequest, response: HttpServletResponse,
                                        e: RuntimeException ->
        response.status = 403
        sendResponse(response, ExceptionResponse.of(HttpStatus.FORBIDDEN.value(), e))
    }

    private fun <T> sendResponse(response: HttpServletResponse, e: T) {
        val body: Response<T> = Response(e)
        val bodyString: String = objectMapper.writeValueAsString(body)
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer
            .write(bodyString)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOrigin("*")
        source.registerCorsConfiguration("/**", config)
        return source
    }


    private inner class JwtResponse constructor(claims: Map<String, String>) {
        private val accessToken: OAuth2AccessToken = toAccessToken(claims, Instant.now(), ACCESS_TOKEN_EXPIRES_IN)
        fun toAccessToken(
            claims: Map<String, String>, issuedAt: Instant,
            expiresIn: Long
        ): OAuth2AccessToken {
            val expiredAt = issuedAt.plus(Duration.ofSeconds(expiresIn))
            val claimSet: JwtClaimsSet = JwtClaimsSet.builder()
                .claims { claim -> claim.putAll(claims) }
                .expiresAt(expiredAt)
                .issuedAt(issuedAt)
                .build()
            val encode: Jwt = jwtEncoder.encode(JwtEncoderParameters.from(claimSet))
            return OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, encode.tokenValue, issuedAt,
                expiredAt
            )
        }
    }


}
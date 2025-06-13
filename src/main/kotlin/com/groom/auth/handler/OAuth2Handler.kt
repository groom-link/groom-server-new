package com.groom.auth.handler

import com.groom.auth.CustomOAuth2User
import com.groom.auth.component.JwtService
import com.groom.auth.interfaces.JwtResponse
import com.groom.common.ResponseSender
import com.groom.domain.auth.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class OAuth2Handler(
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService,
    private val responseSender: ResponseSender
) {
    val oAuth2successLoginHandler =
        { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            val oauth2User = authentication.details as CustomOAuth2User
            val accessToken =
                jwtService.generateToken(oauth2User.authentication.claims, Instant.now())
            responseSender.send(response, JwtResponse(accessToken))
        }
}
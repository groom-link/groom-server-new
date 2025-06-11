package com.groom.auth.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.auth.infrastructure.OAuth2AuthenticationRepository
import com.groom.auth.infrastructure.UserRoleCoreRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component


@Component
class OAuth2AuthenticationWriter(
    private val userService: UserWriter,
    private val oAuth2AuthenticationRepository: OAuth2AuthenticationRepository,
    private val userRoleCoreRepository: UserRoleCoreRepository,
    private val objectMapper: ObjectMapper,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val oAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = oAuth2UserService.loadUser(userRequest)
        val attributes = oAuth2User.attributes
        val grantedUserInfo = toOAuth2User(userRequest.clientRegistration.clientName, attributes)
        val oAuth2Authentication =
            oAuth2AuthenticationRepository.findBy(grantedUserInfo.providerName, grantedUserInfo.providerId)
        val userId = oAuth2Authentication?.userId ?: userService.write(
            User(
                name = grantedUserInfo.nickname,
                email = grantedUserInfo.email
            )
        ).id
        oAuth2AuthenticationRepository.save(
            OAuth2Authentication(
                userId = userId,
                providerUserPk = grantedUserInfo.providerId,
                email = grantedUserInfo.email,
                providerName = grantedUserInfo.providerName,
                accessToken = userRequest.accessToken.tokenValue
            )
        )
        oAuth2User.attributes["user_id"] = userId
        return oAuth2User
    }

    private fun toOAuth2User(clientName: String, attributes: Map<String, Any>): GrantedOAuth2Token {
        when (clientName) {
            "kakao" -> {
                val userInfo: KakaoUserInfo = objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                return GrantedOAuth2Token.fromKakao(userInfo)
            }

            else -> throw RuntimeException("지원하지 않는 간편로그인입니다.")
        }
    }
}
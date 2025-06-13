package com.groom.infrastructure.auth.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.domain.auth.CreateOAuth2UserInformation
import com.groom.domain.auth.OAuth2ProviderName
import org.springframework.stereotype.Component

@Component
internal class OAuth2VOMapper(private val objectMapper: ObjectMapper) {
    fun convert(providerName: OAuth2ProviderName,
                attributes: Map<String, Any>): CreateOAuth2UserInformation {
        when (providerName) {
            OAuth2ProviderName.KAKAO -> {
                val userInfo: KakaoUserInfo =
                    objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                return toOAuth2UserInformation(userInfo)
            }

            else -> throw RuntimeException("지원하지 않는 간편로그인입니다.")
        }
    }

    private fun toOAuth2UserInformation(kakaoUserInfo: KakaoUserInfo): CreateOAuth2UserInformation {
        return CreateOAuth2UserInformation(
            providerName = OAuth2ProviderName.KAKAO,
            providerUserId = kakaoUserInfo.id.toString(),
            email = kakaoUserInfo.kakaoAccount.email,
            nickname = kakaoUserInfo.kakaoAccount.name,
            profileImageUrl = kakaoUserInfo.kakaoAccount.profile.profileImageUrl,
        )
    }
}
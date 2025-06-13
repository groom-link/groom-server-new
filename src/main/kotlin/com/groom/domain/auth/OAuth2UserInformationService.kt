package com.groom.domain.auth

import com.groom.infrastructure.auth.oauth2.OAuth2AuthenticationRepository
import org.springframework.stereotype.Service

@Service
class OAuth2UserInformationService(private val oAuth2AuthenticationRepository: OAuth2AuthenticationRepository) {
    fun findBy(providerName: OAuth2ProviderName, providerUserId: String): OAuth2UserInformation? =
        oAuth2AuthenticationRepository.findBy(providerName, providerUserId)

    fun create(command: CreateOAuth2UserInformationCommand): OAuth2UserInformation {
        return oAuth2AuthenticationRepository.create(command)
    }
}
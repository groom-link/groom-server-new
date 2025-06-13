package com.groom.application.auth

import com.groom.domain.auth.Authentication
import com.groom.domain.auth.OAuth2ProviderName
import com.groom.domain.auth.OAuth2UserInformationCommand

class AuthCriteria private constructor() {
    data class OAuth2Login(val accessToken: String,
                           val providerName: OAuth2ProviderName,
                           val providerUserId: String,
                           val attributes: Map<String, Any>) {
        fun toCreateOAuth2UserInformationCommand(authentication: Authentication): OAuth2UserInformationCommand.Create {
            return OAuth2UserInformationCommand.Create(
                providerName = providerName,
                attributes = attributes,
                authenticationId = authentication.id)
        }
    }
}
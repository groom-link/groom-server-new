package com.groom.application.auth

import com.groom.domain.auth.Authentication
import com.groom.domain.auth.CreateOAuth2UserInformationCommand
import com.groom.domain.auth.OAuth2ProviderName

class AuthCriteria private constructor() {
    data class OAuth2Login(val accessToken: String,
                           val providerName: OAuth2ProviderName,
                           val providerUserId: String,
                           val attributes: Map<String, Any>) {
        fun toCreateOAuth2UserInformationCommand(authentication: Authentication): CreateOAuth2UserInformationCommand {
            return CreateOAuth2UserInformationCommand(
                providerName = providerName,
                attributes = attributes,
                authenticationId = authentication.id)
        }
    }
}
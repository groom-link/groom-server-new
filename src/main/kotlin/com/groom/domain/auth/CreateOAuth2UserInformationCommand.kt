package com.groom.domain.auth

data class CreateOAuth2UserInformationCommand(
    val authenticationId: Long, val providerName: OAuth2ProviderName,
    val attributes: Map<String, Any>
)
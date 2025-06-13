package com.groom.domain.auth


data class CreateOAuth2UserInformation(
    val providerName: OAuth2ProviderName,
    val providerUserId: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
)
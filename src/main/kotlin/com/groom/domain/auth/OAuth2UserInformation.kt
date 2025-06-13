package com.groom.domain.auth


data class OAuth2UserInformation(
    val id: Long,
    val providerName: OAuth2ProviderName,
    val providerUserId: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
)
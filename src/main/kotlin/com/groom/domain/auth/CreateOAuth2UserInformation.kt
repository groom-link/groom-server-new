package com.groom.domain.auth


data class CreateOAuth2UserInformation(
    val providerName: OAuth2ProviderName,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록 후 가능
    val nickname: String,
    val profileImageUrl: String,
)
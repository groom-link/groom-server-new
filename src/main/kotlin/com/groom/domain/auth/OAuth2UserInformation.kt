package com.groom.domain.auth


data class OAuth2UserInformation(
    val id: Long,
    val providerName: OAuth2ProviderName,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    val profileImageUrl: String,
)
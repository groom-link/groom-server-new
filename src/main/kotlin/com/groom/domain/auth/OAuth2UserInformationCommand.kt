package com.groom.domain.auth

class OAuth2UserInformationCommand private constructor() {
    data class Create(val authenticationId: Long, val providerName: OAuth2ProviderName,
                      val attributes: Map<String, Any>)
}
package com.groom.domain.auth

interface OAuth2UserInformationRepository{
    fun findBy(providerName: OAuth2ProviderName,
               providerUserId: String): OAuth2UserInformation?

    fun create(data: CreateOAuth2UserInformationCommand): OAuth2UserInformation
}
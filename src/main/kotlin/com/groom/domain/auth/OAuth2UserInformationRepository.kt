package com.groom.domain.auth

interface OAuth2UserInformationRepository{
    fun findBy(providerName: OAuth2ProviderName,
               providerUserId: String): OAuth2UserInformation?

    fun create(data: OAuth2UserInformationCommand.Create): OAuth2UserInformation
}
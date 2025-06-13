package com.groom.infrastructure.auth.oauth2

import com.groom.domain.auth.CreateOAuth2UserInformationCommand
import com.groom.domain.auth.OAuth2ProviderName
import com.groom.domain.auth.OAuth2UserInformation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class OAuth2AuthenticationRepository internal constructor(private val jpaRepository: OAuth2AuthenticationJpaRepository,
                                                          private val oAuth2VOMapper: OAuth2VOMapper) {
    fun findBy(providerName: OAuth2ProviderName,
               providerUserId: String): OAuth2UserInformation? {
        return jpaRepository.findByProviderNameAndProviderUserId(providerName, providerUserId)
            ?.toDomain()
    }

    fun create(data: CreateOAuth2UserInformationCommand): OAuth2UserInformation {
        val information = oAuth2VOMapper.convert(data.providerName, data.attributes)
        val entity = OAuth2InformationEntity.create(data.authenticationId, information)
        return jpaRepository.save(entity)
            .toDomain()
    }
}

@Repository
internal interface OAuth2AuthenticationJpaRepository :
    JpaRepository<OAuth2InformationEntity, Long> {
    fun findByProviderNameAndProviderUserId(providerName: OAuth2ProviderName,
                                            providerUserId: String): OAuth2InformationEntity?
}
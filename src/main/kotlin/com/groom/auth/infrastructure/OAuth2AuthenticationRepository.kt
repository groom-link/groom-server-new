package com.groom.auth.infrastructure

import com.groom.auth.domain.OAuth2Authentication
import com.groom.auth.domain.OAuth2Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class OAuth2AuthenticationRepository(private val jpaRepository: OAuth2AuthenticationJpaRepository) {
    fun findBy(providerName: OAuth2Provider, providerUserPk: String): OAuth2Authentication? {
        return jpaRepository.findByProviderNameAndProviderUserPk(providerName, providerUserPk)
    }

    fun save(authentication: OAuth2Authentication): OAuth2Authentication {
        return jpaRepository.save(authentication)
    }
}

@Repository
interface OAuth2AuthenticationJpaRepository : JpaRepository<OAuth2Authentication, Long> {
    fun findByProviderNameAndProviderUserPk(providerName: OAuth2Provider, providerUserPk: String): OAuth2Authentication?
}
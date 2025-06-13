package com.groom.domain.auth

import com.groom.infrastructure.auth.authentication.AuthenticationCoreRepository
import org.springframework.stereotype.Component

@Component
class AuthenticationService(private val authenticationCoreRepository: AuthenticationCoreRepository) {
    fun findBy(id: Long): Authentication = authenticationCoreRepository.findBy(id)
    fun createAuthentication(initializeRole: AuthenticationRole = AuthenticationRole.USER): Authentication {
        return authenticationCoreRepository.create(initializeRole)
    }
}
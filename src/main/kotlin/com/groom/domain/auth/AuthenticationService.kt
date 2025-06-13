package com.groom.domain.auth

import org.springframework.stereotype.Component

@Component
class AuthenticationService(private val authenticationRepository: AuthenticationRepository) {
    fun findBy(id: Long): Authentication = authenticationRepository.findBy(id)
    fun createAuthentication(initializeRole: AuthenticationRole = AuthenticationRole.USER): Authentication {
        return authenticationRepository.create(initializeRole)
    }
}
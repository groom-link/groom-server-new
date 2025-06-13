package com.groom.domain.auth

interface AuthenticationRepository {
    fun create(initialRole: AuthenticationRole = AuthenticationRole.USER): Authentication
    fun findBy(id: Long): Authentication
}
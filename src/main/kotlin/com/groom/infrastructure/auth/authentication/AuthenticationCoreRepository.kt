package com.groom.infrastructure.auth.authentication

import com.groom.domain.auth.Authentication
import com.groom.domain.auth.AuthenticationRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AuthenticationCoreRepository internal constructor(private val jpaRepository: AuthenticationJpaRepository) {
    @Transactional
    fun create(initialRole: AuthenticationRole = AuthenticationRole.USER): Authentication {
        val entity = AuthenticationEntity()
        val authentication = jpaRepository.save(entity)
        authentication.addRole(initialRole)
        return authentication.toDomain()
    }

    fun findBy(id: Long): Authentication {
        return jpaRepository.findByIdWithRole(id)
            .toDomain()
    }
}

@Repository
internal interface AuthenticationJpaRepository : JpaRepository<AuthenticationEntity, Long> {
    @Query("select u from authentications u join fetch u.roles where u.id = :id")
    fun findByIdWithRole(id: Long): AuthenticationEntity
}
package com.groom.auth.infrastructure

import com.groom.auth.domain.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRoleCoreRepository(private val jpaRepository: UserRoleJpaRepository) {
    fun findBy(userId: Long): Set<UserRole> {
        return jpaRepository.findByUserId(userId)
    }
}

interface UserRoleJpaRepository : JpaRepository<UserRole, Long> {
    fun findByUserId(userId: Long): Set<UserRole>
}
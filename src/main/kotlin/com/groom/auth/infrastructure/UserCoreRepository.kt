package com.groom.auth.infrastructure

import com.groom.auth.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(private val userJpaRepository: UserJpaRepository) {
    fun save(user: User): User {
        return userJpaRepository.save(user)
    }
    fun findByIdWithRoles(id: Long): User {
        return userJpaRepository.findByIdWithRole(id)
    }
}


@Repository
interface UserJpaRepository : JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.roles where u.id = :id")
    fun findByIdWithRole(id: Long): User
}
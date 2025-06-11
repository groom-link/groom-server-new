package com.groom.auth.domain

import com.groom.auth.infrastructure.UserCoreRepository
import org.springframework.stereotype.Component

@Component
class UserReader(private val userCoreRepository: UserCoreRepository) {
    fun read(id: Long): User {
        return userCoreRepository.findByIdWithRoles(id)
    }
}
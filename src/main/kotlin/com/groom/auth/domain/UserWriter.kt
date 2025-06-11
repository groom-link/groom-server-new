package com.groom.auth.domain

import com.groom.auth.infrastructure.UserCoreRepository
import org.springframework.stereotype.Component

@Component
class UserWriter(private val userCoreRepository: UserCoreRepository){
    fun write(user: User): User {
        return userCoreRepository.save(user)
    }
}
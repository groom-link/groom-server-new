package com.groom.domain.user

interface UserRepository {
    fun createUser(command: CreateUserCommand): User
}
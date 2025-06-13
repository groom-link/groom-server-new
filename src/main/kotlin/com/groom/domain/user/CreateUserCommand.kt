package com.groom.domain.user

data class CreateUserCommand(
    val authenticationId: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
)
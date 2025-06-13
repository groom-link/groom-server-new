package com.groom.domain.user

class UserCommand private constructor() {
    data class Create(
        val authenticationId: Long,
        val email: String,
        val nickname: String,
        val profileImageUrl: String,
    )
}
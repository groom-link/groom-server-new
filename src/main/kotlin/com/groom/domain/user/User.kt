package com.groom.domain.user

import com.groom.domain.TimeStamp

data class User(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
    val timeStamp: TimeStamp
)
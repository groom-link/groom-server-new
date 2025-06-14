package com.groom.domain.auth

enum class AuthenticationRole {
    USER, ADMIN;

    fun toAuthorityString(): String {
        return "ROLE_$name"
    }
}
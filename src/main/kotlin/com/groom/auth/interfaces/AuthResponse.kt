package com.groom.auth.interfaces

class AuthResponse private constructor() {
    data class OAuth2LoginWithAccessToken(val accessToken: String, val refreshToken: String = "")
}
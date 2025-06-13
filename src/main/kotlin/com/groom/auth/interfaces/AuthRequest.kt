package com.groom.auth.interfaces

import java.time.Instant


class AuthRequest private constructor() {
    data class OAuth2LoginWithAccessToken(val accessToken: String, val issuedAt: Instant,
                                          val expiresAt: Instant, val scopes: Set<String>)
}
package com.groom.auth.interfaces

import org.springframework.security.oauth2.core.OAuth2AccessToken

data class JwtResponse(val accessToken: OAuth2AccessToken)
package com.groom.auth.domain

import com.groom.infrastructure.common.Timestamps
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity(name = "oauth2_authentications")
data class OAuth2Authentication(
    @Id
    val userId: Long = 0,
    val providerUserPk: String,
    val email: String,
    @Enumerated(EnumType.STRING)
    val providerName: OAuth2Provider,
    val accessToken: String,
) {
    val timestamp: Timestamps = Timestamps()
}


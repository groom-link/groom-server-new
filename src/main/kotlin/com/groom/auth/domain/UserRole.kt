package com.groom.auth.domain

import com.groom.infrastructure.common.Timestamps
import jakarta.persistence.*

@Entity(name = "user_roles")
data class UserRole(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") val user: User,
    @Enumerated val role: Type
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    val timestamps: Timestamps = Timestamps()

    enum class Type {
        USER, ADMIN
    }
}
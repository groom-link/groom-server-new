package com.groom.auth.domain

import com.groom.infrastructure.common.Timestamps
import jakarta.persistence.*

@Entity
class User(
    val name: String,
    val email: String,
    initialRole: UserRole.Type = UserRole.Type.USER,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    val timestamps: Timestamps = Timestamps()

    @OneToMany(cascade = [(CascadeType.ALL)])
    val roles: Set<UserRole> = setOf(UserRole(this, initialRole))

    val claims: Map<String, String>
        get() =
            mutableMapOf(
                "username" to name,
                "sub" to id.toString(),
                "roles" to roles.joinToString(separator = ",", prefix = "ROLE_") {
                    it.role.name
                })
}
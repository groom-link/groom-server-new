package com.groom.infrastructure.user

import com.groom.domain.user.User
import com.groom.domain.user.UserCommand
import com.groom.infrastructure.common.EntityTimeStamp
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "users")
class UserEntity private constructor(
    @Id
    val authenticationId: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
) {
    val timeStamp: EntityTimeStamp = EntityTimeStamp()

    fun toDomain(): User {
        return User(id = authenticationId,
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            timeStamp = timeStamp.toDomain())
    }

    companion object {
        fun fromCreate(command: UserCommand.Create): UserEntity {
            return UserEntity(authenticationId = command.authenticationId,
                email = command.email,
                nickname = command.nickname,
                profileImageUrl = command.profileImageUrl)
        }
    }
}
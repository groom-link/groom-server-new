package com.groom.infrastructure.auth.oauth2

import com.groom.domain.auth.CreateOAuth2UserInformation
import com.groom.domain.auth.OAuth2ProviderName
import com.groom.domain.auth.OAuth2UserInformation
import com.groom.infrastructure.common.EntityTimeStamp
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "oauth2_informations")
internal data class OAuth2UserInformationEntity(
    val authenticationId: Long,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    @Enumerated(EnumType.STRING)
    val providerName: OAuth2ProviderName,
    val profileImageUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    val timestamp: EntityTimeStamp = EntityTimeStamp()


    fun toDomain(): OAuth2UserInformation {
        return OAuth2UserInformation(id = id,
            providerName = providerName,
            providerUserId = providerUserId,
//            email = email, TODO: 사업자 등록후 가능
            nickname = nickname,
            profileImageUrl = profileImageUrl)
    }


    companion object {
        fun create(authenticationId: Long,
                   information: CreateOAuth2UserInformation): OAuth2UserInformationEntity {
            return OAuth2UserInformationEntity(
                authenticationId = authenticationId,
                providerUserId = information.providerUserId,
//                email = information.email,
                providerName = information.providerName,
                nickname = information.nickname,
                profileImageUrl = information.profileImageUrl,
            )
        }
    }
}


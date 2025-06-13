package com.groom.application.auth

import com.groom.domain.auth.Authentication
import com.groom.domain.auth.AuthenticationService
import com.groom.domain.auth.OAuth2UserInformationService
import com.groom.domain.user.UserCommand
import com.groom.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class AuthFacade(private val userService: UserService,
                 private val oAuth2UserInformationService: OAuth2UserInformationService,
                 private val authenticationService: AuthenticationService) {

    fun loginOrSignUpForOAuth2(criteria: AuthCriteria.OAuth2Login): Authentication {
        var oAuth2UserInformation =
            oAuth2UserInformationService.findBy(providerName = criteria.providerName,
                providerUserId = criteria.providerUserId)
        if (oAuth2UserInformation != null) {
            return authenticationService.findBy(oAuth2UserInformation.id)
        }
        val authentication = authenticationService.createAuthentication()
        oAuth2UserInformation =
            oAuth2UserInformationService.create(criteria.toCreateOAuth2UserInformationCommand(
                authentication))
        userService.create(UserCommand.Create(
            authenticationId = authentication.id,
            email = oAuth2UserInformation.email,
            nickname = oAuth2UserInformation.nickname,
            profileImageUrl = oAuth2UserInformation.profileImageUrl,
        ))
        return authentication
    }
}
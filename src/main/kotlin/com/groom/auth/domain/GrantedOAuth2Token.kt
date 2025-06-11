package com.groom.auth.domain


class GrantedOAuth2Token private constructor(
    val providerName: OAuth2Provider,
    val providerId: String,
    val email: String,
    val nickname: String,
) {


    companion object {
        fun fromKakao(kakaoUserInfo: KakaoUserInfo): GrantedOAuth2Token {
            return GrantedOAuth2Token(
                providerName = OAuth2Provider.KAKAO,
                providerId = kakaoUserInfo.id.toString(),
                email = kakaoUserInfo.kakaoAccount.email,
                nickname = kakaoUserInfo.kakaoAccount.name,
            )
        }
    }
}
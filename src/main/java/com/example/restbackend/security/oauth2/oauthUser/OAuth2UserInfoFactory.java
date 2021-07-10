package com.example.restbackend.security.oauth2.oauthuser;

import java.util.Map;


class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
        return new GoogleOAuth2UserInfo(attributes);
    }
}

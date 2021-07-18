package com.example.restbackend.model.oauth_user;

import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
            return new GoogleOAuth2UserInfo(attributes);
    }
}

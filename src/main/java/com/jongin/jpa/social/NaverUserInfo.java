package com.jongin.jpa.social;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverUserInfo implements SocialUserInfo{
    private final Map<String, Object> oAuth2UserInfo;

    @Override
    public String getProviderId() {
        Map<String,Object> response = (Map<String, Object>) oAuth2UserInfo.get("response");
        return getProvider()+"_"+response.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String,Object> response = (Map<String, Object>) oAuth2UserInfo.get("response");
        return response.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String,Object> response = (Map<String, Object>) oAuth2UserInfo.get("response");
        return response.get("name").toString();
    }
}

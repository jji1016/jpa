package com.jongin.jpa.service;

import com.jongin.jpa.dto.CustomUserDetails;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.repository.MemberRepository;
import com.jongin.jpa.social.GoogleUserInfo;
import com.jongin.jpa.social.KakaoUserInfo;
import com.jongin.jpa.social.NaverUserInfo;
import com.jongin.jpa.social.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //여기서 로그인 정보를 얻을 수 있는 곳
        log.info("userRequest : {}", userRequest);
        Map<String, Object> oAuth2UserInfo = oAuth2User.getAttributes();
        log.info("oAuth2UserInfo : {}", oAuth2UserInfo);
        log.info("userRequest.getClientRegistration().getRegistrationId()==={}",userRequest.getClientRegistration().getRegistrationId()); //google //kakao
        String provider = userRequest.getClientRegistration().getRegistrationId();
        SocialUserInfo socialUserInfo = null;
        if (provider.equals("kakao")) {
            socialUserInfo = new KakaoUserInfo(oAuth2UserInfo);
        }else if (provider.equals("google")) {
            socialUserInfo = new GoogleUserInfo(oAuth2UserInfo);
        }else if (provider.equals("naver")) {
            socialUserInfo = new NaverUserInfo(oAuth2UserInfo);
        }


        Member returnMember = null;
        Optional<Member> findMember = memberRepository.findByUserID(socialUserInfo.getProviderId());
        if(findMember.isPresent()){
            returnMember = findMember.get();
        }else {
            Member member = Member.builder()
                    .userID(socialUserInfo.getProviderId())
                    .userName(socialUserInfo.getName())
                    .userEmail(socialUserInfo.getEmail())
                    .roles("ROLE_MEMBER")
                    .userPW(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()))
                    .build();
            returnMember = memberRepository.save(member);
        }
        return new CustomUserDetails(returnMember, oAuth2UserInfo);
    }

}

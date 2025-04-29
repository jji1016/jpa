package com.jongin.jpa.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
//@RequiredArgsConstructor
public class CustomUserDetails2 implements UserDetails {
    private final MemberDto loggedMemberDto;  //

    public CustomUserDetails2(MemberDto loggedMemberDto) {
        this.loggedMemberDto = loggedMemberDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return loggedMemberDto.getRoles();
//            }
//        });
        authorities.add((GrantedAuthority) () -> loggedMemberDto.getRoles());
        return authorities;
    }

    @Override
    public String getPassword() {
        return loggedMemberDto.getUserPW();
    }

    @Override
    public String getUsername() {
        return loggedMemberDto.getUserID();
    }
}

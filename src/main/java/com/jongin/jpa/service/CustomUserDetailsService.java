package com.jongin.jpa.service;

import com.jongin.jpa.dto.CustomUserDetails;
import com.jongin.jpa.dto.CustomUserDetails2;
import com.jongin.jpa.dto.MemberDto;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        System.out.println("loginUserByUsername");
        Optional<Member> optionalMember = memberRepository.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            MemberDto memberDto = Member.toDto(member);
            return new CustomUserDetails(member);
//            return new CustomUserDetails2(memberDto);
        }
        throw new UsernameNotFoundException("아이디, 패스워드 확인필요");
    }
}

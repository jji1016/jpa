package com.jongin.jpa.service;

import com.jongin.jpa.dto.MemberDto;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    //field 주입 injection
    //@Autowired
    //MemberDao memberDao;

    //생성자 주입방식  불변객체로 만들어 쓴다.
    @Value("${file.upload}") //lombok 아님 springpramework임
    private String upload; //application.yml 에 저장되어있는 file경로 -> C:/upload/

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int signUp(MemberDto memberDto) {
        String rawUserPW = memberDto.getUserPW();
        String encodedUserPW = bCryptPasswordEncoder.encode(rawUserPW);
        memberDto.setUserPW(encodedUserPW);
        memberDto.setRoles("ROLE_MEMBER");
        Member member = MemberDto.toEntity(memberDto);
        Member returnMember = memberRepository.save(member);
        if (returnMember != null) {
            return 1;
        }
        return 0;
    }
}

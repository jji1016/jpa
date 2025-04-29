package com.jongin.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    //Spring Controller에 등록된다.
    //BCryptPasswordEncoder 암호화는 된지만 복호화는 안된다.
    //salt를 만들어서 뿌린다. 즉1234 라는 글자를 쓰더라도 각기 다른 암호가 생성
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

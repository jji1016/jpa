package com.jongin.jpa.config;

import com.jongin.jpa.service.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


//@Configuration → 이 클래스는 설정 클래스임을 의미
//@EnableWebSecurity → Spring Security 활성화
//SecurityFilterChain을 빈으로 등록해서 Spring Security가 쓸 보안 필터 체인을 설정
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // 이러면 bean으로 등록되고 controller에 등록된다. DI할 수 있다

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //.requestMatchers(...)	특정 경로에 대한 접근 설정
        //.permitAll()	인증 없이 접근 허용
        ///admin/**	이 경로는 ADMIN 권한이 있어야 접근 가능
        //.anyRequest().authenticated()	나머지 모든 요청은 로그인(인증) 되어야 함
        httpSecurity.authorizeHttpRequests(
                        (auth) ->
                                auth.requestMatchers("/", "/index/index", "/member/signup", "/member/login", "/mail/find-password", "/css/**", "/images/**")
                                        .permitAll()
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .anyRequest()
                                        .authenticated()

                )
                //CSRF 보안 기능을 비활성화 / 일반적으로는 보안상 권장되지 않지만, 로그인/회원가입 테스트나 API 호출 시 편하게 하기 위해 꺼둘 수 있음
                //loginPage(...)	사용자가 직접 만든 로그인 페이지 경로
                //usernameParameter(...)	로그인 폼에서 ID input의 name 속성 (ex: <input name="userID">)
                //passwordParameter(...)	로그인 폼에서 비밀번호 input의 name 속성
                //loginProcessingUrl(...)	Spring Security가 로그인 처리를 가로채는 URL (form action 이 이 경로여야 함)
                //defaultSuccessUrl(...)	로그인 성공 시 리다이렉트할 경로. true이면 무조건 저기로 감
                //permitAll()	로그인 페이지 접근은 누구나 가능해야 하니까 허용
                .csrf((auth) -> auth.disable())
                .formLogin(
                        (auth) ->
                                auth.loginPage("/member/login")
                                        .usernameParameter("userID") // login.html에 있는 input name="userEmail"
                                        .passwordParameter("userPW")
                                        .loginProcessingUrl("/member/login") //여기서 로그인 처리
                                        .defaultSuccessUrl("/", true)
                                        .failureUrl("/member/login-fail") //redirect로 넘김
                                        .permitAll()
                )
                .logout(auth ->
                        auth.logoutUrl("/member/logout")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                );
        httpSecurity.oauth2Login(auth ->
                auth
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2DetailsService))
        );

        return httpSecurity.build();
    }
}

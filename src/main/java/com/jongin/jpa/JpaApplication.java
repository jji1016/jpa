package com.jongin.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@Configuration        스프링 설정 클래스
//@EnableAutoConfiguration 스프링 부트 자동 설정
//@ComponentScan        @Component, @Service, @Repository 등을 자동으로 스캔
//위 3개의 애너테이션을 한 줄로 합친 거라고 보면됨
@EnableJpaAuditing
//@EnableJpaAuditing: JPA의 Auditing 기능(자동으로 생성일, 수정일 넣어주는 기능)**을 활성화시키는 애너테이션.
//없을경우 @CreatedDate, @LastModifiedDate 같은 애너테이션이 작동 안 함.
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

}

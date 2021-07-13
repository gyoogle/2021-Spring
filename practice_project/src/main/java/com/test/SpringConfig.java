package com.test;

import com.test.repository.MemberRepository;
import com.test.repository.MemoryMemberRepository;
import com.test.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 빈 직접 등록 방법
@Configuration
public class SpringConfig {
    
//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }
//
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}

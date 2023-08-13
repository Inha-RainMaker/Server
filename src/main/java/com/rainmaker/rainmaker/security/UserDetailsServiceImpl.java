package com.rainmaker.rainmaker.security;

import com.rainmaker.rainmaker.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceImpl {
    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return username -> RainMakerPrinciple.of(memberService.findMemberByNickName(username));
    }
}

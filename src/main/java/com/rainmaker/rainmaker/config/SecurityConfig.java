package com.rainmaker.rainmaker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainmaker.rainmaker.dto.common.ErrorResponse;
import com.rainmaker.rainmaker.exception.ExceptionType;
import com.rainmaker.rainmaker.security.JwtAuthenticationFilter;
import com.rainmaker.rainmaker.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String BASE_URL = "/api";
    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**",
            "/app/auth/local/**" // 회원가입 api 및 로그인 api에 접속하기 위해 인증 제외하기 위해 추가
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                // JWT 기반 인증이기 때문에 session 사용 x
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .mvcMatchers(BASE_URL + "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 권한 문제가 발생했을 때 이 부분을 호출한다.
                    log.error("SecurityConfig.SecurityFilterChain.accessDeniedHandler() ex={}", String.valueOf(accessDeniedException));

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=UTF-8");

                    int errorCode = ExceptionType.ACCESS_DENIED_EXCEPTION.getErrorCode();
                    String errorMessage = ExceptionType.ACCESS_DENIED_EXCEPTION.getErrorMessage();
                    ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    // 인증문제가 발생했을 때 이 부분을 호출한다.
                    log.error("SecurityConfig.SecurityFilterChain.authenticationEntryPoint() ex={}", String.valueOf(authException));

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=UTF-8");

                    int errorCode = ExceptionType.AUTHENTICATION_EXCEPTION.getErrorCode();
                    String errorMessage = ExceptionType.AUTHENTICATION_EXCEPTION.getErrorMessage();
                    ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                }).and()
                .build();
    }
}

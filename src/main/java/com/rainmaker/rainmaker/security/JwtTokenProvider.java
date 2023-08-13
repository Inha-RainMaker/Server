package com.rainmaker.rainmaker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final long EXPIRED_DURATION = 7 * 24 * 60 * 60 * 1000L;   // Token 유효시간 일주일

    private final UserDetailsService userDetailsService;

    @Value("${JWT_SECRET_KEY}")
    private String salt;
    private Key secretKey;

    /**
     * 객체 초기화.
     * jwt secret key를 Base64로 인코딩
     */
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Jwt token을 생성하여 반환한다.
     *
     * @param nickName 로그인하려는 사용자의 아이디 (닉네임)
     * @return 생성한 jwt token
     */
    public String createToken(String nickName) {
        // TODO: 권한은 추후에 설정
        Claims claims = Jwts.claims()
                .setSubject(nickName);

        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setSubject(nickName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRED_DURATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Jwt token에서 사용자 정보 조회 후 security login 과정(UsernamePasswordAuthenticationToken)을 수행한다.
     *
     * @param token Jwt token
     * @return Token을 통해 조회한 사용자 정보
     */
    public Authentication getAuthentication(String token) {
        UserDetails principal = userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    /**
     * 토큰에서 회원 정보(username)를 추출
     *
     * @param token Jwt token
     * @return 추출한 회원 정보(username == nickName)
     */
    private String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Request의 header에서 token을 읽어온다.
     *
     * @param request HttpServletRequest 객체
     * @return Header에서 추출한 token
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    /**
     * 토큰의 유효성, 만료일자 검증
     *
     * @param token Jwt token
     */
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            log.error("JwtTokenProvider.validateToken() ex={}", String.valueOf(e));
            throw e;
        }
    }

}

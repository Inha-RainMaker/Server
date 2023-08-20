package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.major.MajorDto;
import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Gender;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import com.rainmaker.rainmaker.exception.member.MemberPKNotFoundException;
import com.rainmaker.rainmaker.repository.MajorRepository;
import com.rainmaker.rainmaker.repository.MemberRepository;
import com.rainmaker.rainmaker.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void 유저정보가_주어지면_회원가입을_수행한다() throws Exception {
        //given
        Major major = new Major("컴퓨터공학과", "소프트웨어융합대학");
        majorRepository.save(major);

        MemberDto memberDto = MemberDto.of("홍길동", "천방지축 도사", "password1!",
                "010-1234-1234", 1, Gender.MALE, MajorDto.from(major));

        //when
        Long savedMemberId = authService.signUp(memberDto);
        Member findMember = memberRepository.findById(savedMemberId)
                .orElseThrow(MemberPKNotFoundException::new);

        //then
        assertThat(findMember.getId()).isEqualTo(savedMemberId);
    }

    @Test
    public void 닉네임이_주어지면_jwt토큰을_생성한다() {
        //given
        String nickName = "별명1";
        String createdToken = jwtTokenProvider.createToken(nickName);

        //when
        String result = authService.createToken(nickName);

        //then
        System.out.println("createdToken = " + createdToken);
        System.out.println("result = " + result);
        assertThat(result).isEqualTo(createdToken);
    }

    @Test
    public void 유저네임과_비밀번호가_주어지면_로그인을_수행한다() throws Exception {
        //given
        Major major = new Major("컴퓨터공학과", "소프트웨어융합대학");
        majorRepository.save(major);

        MemberDto memberDto = MemberDto.of("홍길동", "천방지축 도사", "password1!",
                "010-1234-1234", 1, Gender.MALE, MajorDto.from(major));

        Long savedMemberId = authService.signUp(memberDto);

        String nickName = "천방지축 도사";
        String password = "password1!";

        //when
        String jwtToken = authService.login(nickName, password);

        //then
        System.out.println("jwtToken = " + jwtToken);
    }
}

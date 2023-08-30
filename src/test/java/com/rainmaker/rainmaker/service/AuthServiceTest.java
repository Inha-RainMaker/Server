package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.major.MajorDto;
import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Gender;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import com.rainmaker.rainmaker.entity.ProfileImage;
import com.rainmaker.rainmaker.repository.MajorRepository;
import com.rainmaker.rainmaker.repository.MemberRepository;
import com.rainmaker.rainmaker.repository.ProfileImageRepository;
import com.rainmaker.rainmaker.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MajorRepository majorRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private ProfileImageRepository profileImageRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void 유저정보가_주어지면_회원가입을_수행한다() throws Exception {
        //given
        MemberDto memberDto = createMemberDto();
        given(profileImageRepository.existsDefaultMemberProfileImage()).willReturn(true);
        given(profileImageRepository.getDefaultMemberProfileImage()).willReturn(createProfileImage());
        given(memberRepository.save(any(Member.class))).willReturn(createMember());
        given(majorRepository.findByName(anyString())).willReturn(Optional.of(createMajor()));
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

        //when
        authService.signUp(memberDto, null);

        //then
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    public void 닉네임이_주어지면_jwt토큰을_생성한다() {
        //given
        String nickName = "별명1";
        String expected = "토큰";
        given(jwtTokenProvider.createToken(nickName)).willReturn(expected);

        //when
        String result = authService.createToken(nickName);

        //then
        assertThat(result).isEqualTo(expected);
        then(jwtTokenProvider).should().createToken(nickName);
    }

    private MajorDto createMajorDto() {
        return MajorDto.of("컴퓨터공학과", "소프트웨어융합대학");
    }

    private MemberDto createMemberDto() {
        return MemberDto.of(
                "홍길동",
                "천방지축 도사",
                "pwd1234!",
                "010-1234-1234",
                2,
                Gender.MALE,
                createMajorDto()
        );
    }

    private Member createMember() {
        Member member = createMemberDto().toEntity(createMajor(), createProfileImage());
        ReflectionTestUtils.setField(member, "id", 1L);

        return member;
    }

    private Major createMajor() {
        return Major.builder()
                .name("컴퓨터공학과")
                .department("소프트웨어융합대학")
                .build();
    }

    private ProfileImage createProfileImage() {
        return ProfileImage.builder()
                .fileName("test")
                .storedFileName("test")
                .url("url")
                .build();
    }
}

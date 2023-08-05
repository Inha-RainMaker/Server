package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.MajorDto;
import com.rainmaker.rainmaker.dto.MemberSignUpDto;
import com.rainmaker.rainmaker.entity.Gender;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception{
        //given
        Major major = new Major("컴퓨터공학과", "소프트웨어융합대학");
        em.persist(major);

        MajorDto majorDto = MajorDto.from(major);

        MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
                .gender(Gender.FEMALE)
                .name("member1")
                .nickName("별명1")
                .password("1234")
                .checkedPassword("1234")
                .email("1111@naver.com")
                .grade(2)
                .build();
        memberSignUpDto.setMajorDto(major);

        //when
        Long savedMemberId = authService.signUp(memberSignUpDto);
        Member findMember = em.createQuery("select m from Member m where m.id = :memberId", Member.class)
                .setParameter("memberId", savedMemberId)
                .getSingleResult();

        //then
        assertThat(findMember.getId()).isEqualTo(savedMemberId);
    }

    // TODO 추후 로그인 구현 시 테스트 코드 작성 필요
    @Test
    public void 비밀번호_일치_검증() throws Exception{
        //given
        
        
        //when
        
        //then
//        if(passwordEncoder.matches(newPwd, originPwd){
//            System.out.println("true");
//        } else {
//            System.out.println("false");
//        }
    }
}

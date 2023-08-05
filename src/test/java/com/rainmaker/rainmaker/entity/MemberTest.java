package com.rainmaker.rainmaker.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Major major = new Major("컴퓨터공학과", "소프트웨어융합대학");
        em.persist(major);

        Member member = Member.builder()
                .name("유저1")
                .nickName("별명1")
                .grade(1)
                .gender(Gender.FEMALE)
                .major(major)
                .build();
        em.persist(member);

        //when
        Member findUser = em.createQuery("select m from Member m", Member.class)
                .getSingleResult();

        //then
        assertThat(findUser).isEqualTo(member);
    }
}
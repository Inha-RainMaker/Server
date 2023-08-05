package com.rainmaker.rainmaker;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class JPATest {

    @Autowired
    EntityManager em;

    @Test
    public void test(){
//        Member user = new Member();
//        em.persist(user);
//
//        Member findUser = em.createQuery("select u from Member u", Member.class)
//                .getSingleResult();
//
//        assertThat(findUser.getId()).isEqualTo(user.getId());
    }
}

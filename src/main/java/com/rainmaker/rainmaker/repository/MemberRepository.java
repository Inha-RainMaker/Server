package com.rainmaker.rainmaker.repository;

import com.rainmaker.rainmaker.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findByNickName(String nickName);
}

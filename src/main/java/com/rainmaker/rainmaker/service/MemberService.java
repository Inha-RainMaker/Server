package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.exception.member.NickNameNotFoundException;
import com.rainmaker.rainmaker.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 전체 멤버 조회
     */
    public List<MemberDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberDto::from)
                .toList();
    }

    /**
     * 닉네임(아이디)로 멤버 조회
     */
    public MemberDto findMemberByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .map(MemberDto::from)
                .orElseThrow(NickNameNotFoundException::new);

    }
}

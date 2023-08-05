package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.MemberSignUpDto;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import com.rainmaker.rainmaker.repository.MajorRepository;
import com.rainmaker.rainmaker.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 정보(){@link MemberSignUpDto}를 입력받아
     * @param memberSignUpDto 새로운 회원 정보가 담긴 DTO
     * @return 생성한 회원의 식별 id
     * @throws Exception
     */
    @Transactional
    public Long signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        // 아이디(닉네임) 검증
        if(memberRepository.findByNickName(memberSignUpDto.getNickName()).isPresent()){
            throw new Exception("이미 존재하는 닉네임(아이디)입니다."); // TODO Exception 바꾸기
        }

        // 비밀번호 검증
        if(!memberSignUpDto.getPassword().equals(memberSignUpDto.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다."); // TODO Exception 바꾸기
        }

        // TODO 전화번호 형식 검증 로직 추가 필요

        Major major = majorRepository.findByName(memberSignUpDto.getMajorDto().getName());

        Member member = memberRepository.save(memberSignUpDto.toEntity(major));
        member.encodePassword(passwordEncoder);
        member.addUserAuthority();

        return member.getId();
    }
}

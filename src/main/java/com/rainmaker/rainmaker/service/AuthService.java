package com.rainmaker.rainmaker.service;

import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import com.rainmaker.rainmaker.entity.ProfileImage;
import com.rainmaker.rainmaker.exception.auth.JwtUnauthroziedException;
import com.rainmaker.rainmaker.exception.major.MajorNotFoundException;
import com.rainmaker.rainmaker.exception.member.NickNameDuplicateException;
import com.rainmaker.rainmaker.exception.member.NickNameNotFoundException;
import com.rainmaker.rainmaker.repository.MajorRepository;
import com.rainmaker.rainmaker.repository.MemberRepository;
import com.rainmaker.rainmaker.repository.ProfileImageRepository;
import com.rainmaker.rainmaker.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final S3FileService s3FileService;
    private final ProfileImageRepository profileImageRepository;
    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * @param memberDto 새로운 회원 정보가 담긴 DTO
     * @param imageFile 회원의 프로필 이미지
     * @return 생성한 회원의 식별 id
     */
    @Transactional
    public Long signUp(MemberDto memberDto, MultipartFile imageFile) {

        // 닉네임 중복 체크
        if (memberRepository.findByNickName(memberDto.getNickName()).isPresent()) {
            throw new NickNameDuplicateException();
        }

        // 학과 조회
        Major major = majorRepository.findByName(memberDto.getMajorDto().getName())
                .orElseThrow(MajorNotFoundException::new);

        // 프로필 이미지 생성
        ProfileImage profileImage = getProfileImage(imageFile);

        // 회원가입
        Member member = memberRepository.save(memberDto.toEntity(major, profileImage));
        member.encodePassword(passwordEncoder);
        member.addUserAuthority();

        return member.getId();
    }

    /**
     * imageFile 이 null 이 아닐 경우, S3 에 저장하고 ProfileImage 를 생성한다.
     * imageFile 이 null 일 경우, 기본 프로필 이미지의 ProfileImage 를 생성한다.
     *
     * @param imageFile 회원의 프로필 이미지로 설정하려는 이미지 파일
     * @return 생성된 ProfileImage 객체
     */
    private ProfileImage getProfileImage(MultipartFile imageFile) {
        if (imageFile == null) {
            return getDefaultProfileImage();
        } else {
            return s3FileService.saveFile(imageFile);
        }
    }

    /**
     * 기본 프로필 이미지에 대해 ProfileImage 객체를 반환
     *
     * @return 기본 프로필 이미지의 ProfileImage 객체
     */
    private ProfileImage getDefaultProfileImage() {
        if (profileImageRepository.existsDefaultMemberProfileImage()) {
            return profileImageRepository.getDefaultMemberProfileImage();
        } else {
            return s3FileService.saveMemberDefaultProfileImage();
        }
    }

    /**
     * 회원의 nickName을 입력받아 JWT token 을 생성하여 반환한다.
     *
     * @param nickName 로그인하려는 사용자의 nickName
     * @return 생성된 JWT token
     */
    public String createToken(String nickName) {
        return jwtTokenProvider.createToken(nickName);
    }

    /**
     * 로그인하려는 회원의 아이디(닉네임)과 비밀번호를 입력받아 로그인 정보를 검증한다.
     * 로그인에 성공하면 JWT token 을 생성하여 반환한다.
     *
     * @param nickName 로그인하려는 사용자의 nickName
     * @param password 로그인하려는 사용자의 비밀번호
     * @return 생성된 JWT token
     */
    public String login(String nickName, String password) {
        Member member = memberRepository.findByNickName(nickName)
                .orElseThrow(NickNameNotFoundException::new);

        if (passwordEncoder.matches(password, member.getPassword())) {
            return createToken(nickName);
        } else {
            throw new JwtUnauthroziedException();
        }
    }
}

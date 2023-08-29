package com.rainmaker.rainmaker.dto.member.request;

import com.rainmaker.rainmaker.dto.major.request.MajorRequest;
import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 시 사용하는 Request
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignupRequest {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, message = "이름이 너무 짧습니다.")
    @Schema(example = "홍길동", description = "사용자의 실명")
    private String name;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min = 2, message = "별명이 너무 짧습니다.")
    @Schema(example = "천방지축 홍길동", description = "사용자의 닉네임(아이디)")
    private String nickName;


    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(example = "pwd1234!", description = "비밀번호")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 다시 한번 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Schema(example = "pwd1234!", description = "비밀번호 재확인을 위한 체크용 비밀번호")
    private String checkedPassword;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호는 010-1234-1234 또는 01012341234 형식이어야 합니다.")
    @Schema(example = "010-1234-1234", description = "휴대폰 번호")
    private String phone;

    @NotNull(message = "학년을 입력해주세요")
    @Schema(example = "4", description = "학년")
    private int grade;

    @Schema(example = "MALE", description = "성별")
    private Gender gender;

    @NotNull
    private MajorRequest majorRequest;


    public MemberDto toDto() {
        return MemberDto.of(getName(), getNickName(), getPassword(), getPhone(), getGrade(), getGender(),
                getMajorRequest().toDto());
    }
}

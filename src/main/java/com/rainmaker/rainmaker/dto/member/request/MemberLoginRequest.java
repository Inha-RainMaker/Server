package com.rainmaker.rainmaker.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 로그인 시 사용하는 Request
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginRequest {

    @NotBlank(message = "아이디(별명)을 입력해주세요")
    @Size(min = 2, message = "아이디(별명)이 너무 짧습니다.")
    @Schema(example = "천방지축 홍길동", description = "사용자의 아이디(닉네임)")
    private String nickName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(example = "pwd1234!", description = "비밀번호")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}

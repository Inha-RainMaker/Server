package com.rainmaker.rainmaker.dto.member.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreateResponse {

    @Schema(example = "1", description = "회원가입 성공한 사용자의 PK")
    private Long memberId;

    public static MemberCreateResponse of(Long memberId) {
        return new MemberCreateResponse(memberId);
    }
}

package com.rainmaker.rainmaker.dto.member.response;

import com.rainmaker.rainmaker.dto.major.response.MajorInfoResponse;
import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 멤버 검색 조회시 사용되는 Response
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

    @Schema(example = "1", description = "멤버의 고유 식별자 (PK)")
    private Long id;

    private MajorInfoResponse major;

    @Schema(example = "천방지축 홍길동", description = "멤버의 닉네임 (아이디)")
    private String nickName;

    @Schema(example = "홍길동", description = "멤버의 실명")
    private String name;

    @Schema(example = "4", description = "멤버의 학년")
    private int grade;

    @Schema(example = "MALE", description = "멤버의 성별")
    private Gender gender;

    @Schema(example = "010-1234-1234", description = "멤버의 휴대폰 번호")
    private String phone;

    // TODO ROLE 은 아직 안넣음. 추후 필요할 시 추가 예정

    public static MemberInfoResponse from(MemberDto memberDto) {
        return new MemberInfoResponse(
                memberDto.getId(),
                MajorInfoResponse.from(memberDto.getMajorDto()),
                memberDto.getNickName(),
                memberDto.getUserName(),
                memberDto.getGrade(),
                memberDto.getGender(),
                memberDto.getPhone()
        );
    }
}

package com.rainmaker.rainmaker.dto.major.response;

import com.rainmaker.rainmaker.dto.major.MajorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 전공 정보 조회 시 사용되는 Response
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MajorInfoResponse {

    @Schema(example = "컴퓨터공학과", description = "학과 이름")
    private String name;

    @Schema(example = "소프트웨어융합대학", description = "학과가 소속한 단과대학 이름")
    private String department;

    public static MajorInfoResponse from(MajorDto majorDto) {
        return new MajorInfoResponse(
                majorDto.getName(),
                majorDto.getDepartment()
        );
    }
}

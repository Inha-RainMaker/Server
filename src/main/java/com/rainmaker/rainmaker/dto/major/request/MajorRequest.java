package com.rainmaker.rainmaker.dto.major.request;


import com.rainmaker.rainmaker.dto.major.MajorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 회원가입 시에 사용하는 MemberFormRequest의 필드로 사용됨
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorRequest {

    @Schema(example = "컴퓨터공학과", description = "학과 이름")
    @NotEmpty
    private String name;


    @Schema(example = "소프트웨어융합대학", description = "학과 소속 단과대학 이름")
    @NotEmpty
    private String department;


    public MajorDto toDto() {
        return MajorDto.of(getName(), getDepartment());
    }
}

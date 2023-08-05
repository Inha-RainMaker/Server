package com.rainmaker.rainmaker.dto;

import com.rainmaker.rainmaker.entity.Gender;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberSignUpDto {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min=2, message = "이름이 너무 짧습니다.")
    private String name;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min=2, message = "별명이 너무 짧습니다.")
    private String nickName;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호를 다시 한번 입력해주세요")
    private String checkedPassword;

    @NotNull(message = "학년을 입력해주세요")
    private int grade;

    private Gender gender;

    private MajorDto majorDto;

    public Member toEntity(Major major){
        return Member.builder()
                .name(this.getName())
                .password(this.getPassword())
                .grade(this.getGrade())
                .gender(this.getGender())
                .nickName(this.getNickName())
                .major(major)
                .build();
    }

    public void setMajorDto(Major major){
        majorDto = MajorDto.from(major);
    }
}
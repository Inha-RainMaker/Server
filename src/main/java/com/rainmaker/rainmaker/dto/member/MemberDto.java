package com.rainmaker.rainmaker.dto.member;

import com.rainmaker.rainmaker.dto.major.MajorDto;
import com.rainmaker.rainmaker.entity.Gender;
import com.rainmaker.rainmaker.entity.Major;
import com.rainmaker.rainmaker.entity.Member;
import com.rainmaker.rainmaker.entity.ProfileImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String phone;
    private int grade;
    private Gender gender;
    private MajorDto majorDto;

    public static MemberDto of(Long id, String userName, String nickName, String password, String phone,
                               int grade, Gender gender, MajorDto majorDto) {
        return new MemberDto(id, userName, nickName, password, phone, grade, gender, majorDto);
    }

    public static MemberDto of(String userName, String nickName, String password, String phone,
                               int grade, Gender gender, MajorDto majorDto) {
        return new MemberDto(null, userName, nickName, password, phone, grade, gender, majorDto);
    }

    public static MemberDto from(Member member) {
        return MemberDto.of(
                member.getId(), member.getUserName(), member.getNickName(), member.getPassword(),
                member.getPhone(), member.getGrade(), member.getGender(), MajorDto.from(member.getMajor()));
    }

    public Member toEntity(Major major, ProfileImage profileImage) {
        return Member.builder()
                .profileImage(profileImage)
                .major(major)
                .userName(getUserName())
                .nickName(getNickName())
                .password(getPassword())
                .grade(getGrade())
                .gender(getGender())
                .phone(getPhone())
                .build();
    }
}

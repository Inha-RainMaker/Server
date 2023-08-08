package com.rainmaker.rainmaker.dto;

import com.rainmaker.rainmaker.entity.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String userName;
    private String nickName;
    private String password;

    public MemberDto(Long id, String userName, String nickName, String password) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
    }

    public static MemberDto of(Long id, String userName, String nickName, String password) {
        return new MemberDto(id, userName, nickName, password);
    }

    public static MemberDto from(Member member) {
        return MemberDto.of(member.getId(), member.getName(), member.getNickName(), member.getPassword());
    }
}

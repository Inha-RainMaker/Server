package com.rainmaker.rainmaker.dto;

import com.rainmaker.rainmaker.entity.Member;
import lombok.Data;

@Data
public class MemberDto {
    private String userName;
    private String nickName;

    public MemberDto(String userName, String nickName) {
        this.userName = userName;
        this.nickName = nickName;
    }

    public static MemberDto of(String userName, String nickName){
        return new MemberDto(userName, nickName);
    }

    public static MemberDto from(Member member){
        return MemberDto.of(member.getName(), member.getNickName());
    }
}

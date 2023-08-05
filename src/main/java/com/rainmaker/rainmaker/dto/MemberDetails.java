package com.rainmaker.rainmaker.dto;


import lombok.Data;

// 현재 로그인중인 Member 정보를 조회할 때 사용하는 클래스
@Data
public class MemberDetails {
    private String username;

    private String nickName;

    private int grade;
}

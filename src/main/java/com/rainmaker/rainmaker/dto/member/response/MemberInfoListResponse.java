package com.rainmaker.rainmaker.dto.member.response;

import com.rainmaker.rainmaker.dto.member.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 여러 명의 멤버 검색 조회 시 사용되는 Response
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoListResponse {
    private List<MemberInfoResponse> memberList;

    public static MemberInfoListResponse from(List<MemberDto> memberDtoList) {
        return new MemberInfoListResponse(
                memberDtoList.stream()
                        .map(MemberInfoResponse::from)
                        .collect(Collectors.toUnmodifiableList())
        );
    }
}

package com.rainmaker.rainmaker.controller;

import com.rainmaker.rainmaker.dto.common.DataResponse;
import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.dto.member.response.MemberInfoListResponse;
import com.rainmaker.rainmaker.security.RainMakerPrinciple;
import com.rainmaker.rainmaker.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "멤버 API")
@RequiredArgsConstructor
@RequestMapping("/app/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * TODO 추후 조건에 따라 멤버 리스트 조회하는 API 로 변경 예정
     */
    @Operation(
            summary = "전체 멤버 조회 API",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping
    public ResponseEntity<DataResponse<MemberInfoListResponse>> findMembers(
            @Parameter(hidden = true) @AuthenticationPrincipal RainMakerPrinciple principle
    ) {
        List<MemberDto> memberDtos = memberService.findAll();

        MemberInfoListResponse response = MemberInfoListResponse.from(memberDtos);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }
}

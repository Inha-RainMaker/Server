package com.rainmaker.rainmaker.controller;

import com.rainmaker.rainmaker.dto.common.DataResponse;
import com.rainmaker.rainmaker.dto.member.request.MemberFormRequest;
import com.rainmaker.rainmaker.dto.member.response.MemberCreateResponse;
import com.rainmaker.rainmaker.security.RainMakerPrinciple;
import com.rainmaker.rainmaker.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "로그인 및 회원가입 API")
@RequiredArgsConstructor
@RequestMapping("/app/auth")
@RestController
public class AuthController {

    private final AuthService authService;


    @Operation(
            summary = "JWT 회원가입 API"
    )
    @PostMapping("/local/new")
    public ResponseEntity<DataResponse<MemberCreateResponse>> signup(
            @Valid @RequestBody MemberFormRequest memberFormRequest
    ){
        Long memberId = authService.signUp(memberFormRequest.toDto());

        MemberCreateResponse response = MemberCreateResponse.of(memberId);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }


}


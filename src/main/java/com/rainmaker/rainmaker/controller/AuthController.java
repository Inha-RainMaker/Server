package com.rainmaker.rainmaker.controller;

import com.rainmaker.rainmaker.dto.common.DataResponse;
import com.rainmaker.rainmaker.dto.member.request.MemberFormRequest;
import com.rainmaker.rainmaker.dto.member.request.MemberLoginRequest;
import com.rainmaker.rainmaker.dto.member.response.MemberCreateResponse;
import com.rainmaker.rainmaker.dto.member.response.MemberLoginResponse;
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
            summary = "회원가입 API"
    )
    @PostMapping("/local/new")
    public ResponseEntity<DataResponse<MemberCreateResponse>> signup(
            @Valid @RequestBody MemberFormRequest memberFormRequest
    ) {
        Long memberId = authService.signUp(memberFormRequest.toDto());

        MemberCreateResponse response = MemberCreateResponse.of(memberId);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "JWT 로그인 요청 API",
            description = "<p>로그인하려는 아이디와 패스워드로 서버에 로그인합니다.</p>" +
                    "<p>로그인에 성공하면 jwt access token 을 응답합니다.</p>" +
                    "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>" +
                    "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>"
    )
    @PostMapping("/local")
    public ResponseEntity<DataResponse<MemberLoginResponse>> login(
            @Valid @RequestBody MemberLoginRequest memberLoginRequest
    ) {
        String jwtToken = authService.login(memberLoginRequest.getNickName(), memberLoginRequest.getPassword());

        MemberLoginResponse response = MemberLoginResponse.of(jwtToken);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }

    @Hidden
    @Operation(
            summary = "개발용 테스트 API (사용하지 말 것)",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping("/check")
    public ResponseEntity<DataResponse<RainMakerPrinciple>> check(
            @Parameter(hidden = true) @AuthenticationPrincipal RainMakerPrinciple principle
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse<>(principle));
    }


}


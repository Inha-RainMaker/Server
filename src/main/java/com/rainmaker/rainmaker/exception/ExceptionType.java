package com.rainmaker.rainmaker.exception;

import com.rainmaker.rainmaker.exception.auth.JwtUnauthroziedException;
import com.rainmaker.rainmaker.exception.common.CustomException;
import com.rainmaker.rainmaker.exception.file.MultipartFileNotReadableException;
import com.rainmaker.rainmaker.exception.member.MemberPKNotFoundException;
import com.rainmaker.rainmaker.exception.member.NickNameDuplicateException;
import com.rainmaker.rainmaker.exception.member.NickNameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Error code 규약
 * Member : 1XXX
 * File : 2XXX
 */
@AllArgsConstructor
@Getter
public enum ExceptionType {
    // Common
    UNHANDLED_EXCEPTION(0, "알 수 없는 서버 에러가 발생했습니다.", null),
    BAD_REQUEST_EXCEPTION(1, "요청 데이터가 잘못되었습니다.", null),
    ACCESS_DENIED_EXCEPTION(2, "권한이 유효하지 않습니다.", null),
    AUTHENTICATION_EXCEPTION(3, "인증 과정에서 문제가 발생했습니다.", null),

    // Auth
    JWT_UNAUTHORIZED_EXCEPTION(100, "JWT 토큰이 잘못되었습니다.", JwtUnauthroziedException.class),

    // Member
    MEMBER_PK_NOT_FOUND_EXCEPTION(1000, "멤버 식별자로 멤버를 조회할 수 없습니다.", MemberPKNotFoundException.class),
    NICKNAME_NOT_FOUND_EXCEPTION(1100, "해당 닉네임을 가지는 회원이 없습니다.", NickNameNotFoundException.class),
    NICKNAME_DUPLICATE_EXCEPTION(1200, "이미 사용중인 닉네임입니다.", NickNameDuplicateException.class),

    // File
    MULTIPART_FILE_NOT_READABLE_EXCEPTION(2000, "multipart 파일을 읽을 수 없습니다.", MultipartFileNotReadableException.class);

    private final int errorCode;
    private final String errorMessage;
    private final Class<? extends Exception> type;

    public static ExceptionType from(Class<? extends CustomException> classType) {
        return findExceptionType(classType, UNHANDLED_EXCEPTION);
    }

    public static ExceptionType fromByAuthenticationFailed(Class<? extends Exception> classType) {
        return findExceptionType(classType, AUTHENTICATION_EXCEPTION);
    }

    private static ExceptionType findExceptionType(
            Class<? extends Exception> classType,
            ExceptionType defaultExceptionType
    ) {
        return Arrays.stream(values())
                .filter(it -> Objects.nonNull(it.type) && it.type.equals(classType))
                .findFirst()
                .orElse(defaultExceptionType);
    }
}

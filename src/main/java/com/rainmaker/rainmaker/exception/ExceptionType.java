package com.rainmaker.rainmaker.exception;

import com.rainmaker.rainmaker.exception.common.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Error code 규약
 * Member : 1XXX
 * 추후 추가 예정
 */
@AllArgsConstructor
@Getter
public enum ExceptionType {
    // Common
    UNHANDLED_EXCEPTION(0, "알 수 없는 서버 에러가 발생했습니다.", null),
    BAD_REQUEST_EXCEPTION(1, "요청 데이터가 잘못되었습니다.", null),
    ACCESS_DENIED_EXCEPTION(2, "권한이 유효하지 않습니다.", null),
    AUTHENTICATION_EXCEPTION(3, "인증 과정에서 문제가 발생했습니다.", null);

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

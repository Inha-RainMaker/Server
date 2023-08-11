package com.rainmaker.rainmaker.exception.common;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String optionalMessage) {
        super(HttpStatus.UNAUTHORIZED, optionalMessage);
    }
}
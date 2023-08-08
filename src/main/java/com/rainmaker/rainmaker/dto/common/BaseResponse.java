package com.rainmaker.rainmaker.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseResponse {
    // 요청 처리 성공 시 true, 아니면 false
    private boolean isSuccess;
}

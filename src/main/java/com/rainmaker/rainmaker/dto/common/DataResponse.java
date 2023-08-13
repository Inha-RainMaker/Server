package com.rainmaker.rainmaker.dto.common;

import lombok.Getter;

@Getter
public class DataResponse<T> extends BaseResponse {

    // 응답 데이터
    private T data;
    public DataResponse(T data) {
        super(true);
        this.data = data;
    }
}

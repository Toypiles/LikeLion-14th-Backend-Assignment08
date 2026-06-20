package com.likelion.likelionassignmentcrud.common.template;

import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.common.response.code.SuccessCode;

public record ApiResTemplate<T>(
        int code,
        String message,
        T data
) {

    public static ApiResTemplate<Void> successWithNoContent(SuccessCode successCode) {
        return new ApiResTemplate<>(
                successCode.getHttpStatusCode(),
                successCode.getMessage(),
                null
        );
    }

    public static <T> ApiResTemplate<T> successResponse(SuccessCode successCode, T data) {
        return new ApiResTemplate<>(
                successCode.getHttpStatusCode(),
                successCode.getMessage(),
                data
        );
    }

    public static ApiResTemplate<Void> errorResponse(ErrorCode errorCode) {
        return new ApiResTemplate<>(
                errorCode.getHttpStatusCode(),
                errorCode.getMessage(),
                null
        );
    }

    public static ApiResTemplate<Void> errorResponse(ErrorCode errorCode, String customMessage) {
        return new ApiResTemplate<>(
                errorCode.getHttpStatusCode(),
                customMessage,
                null
        );
    }
}

package com.likelion.likelionassignmentcrud.common.response.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    DIRECTOR_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 감독이 없습니다. directorId = "),
    MOVIE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 영화가 없습니다. movieId = "),
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다. - "),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출 중 오류가 발생했습니다."),
    KOBIS_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "KOBIS 서버와 연결할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}

package com.likelion.likelionassignmentcrud.common.response.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    GET_SUCCESS(HttpStatus.OK, "성공적으로 조회했습니다."),
    DIRECTOR_UPDATE_SUCCESS(HttpStatus.OK, "감독이 성공적으로 수정되었습니다."),
    MOVIE_UPDATE_SUCCESS(HttpStatus.OK, "영화가 성공적으로 수정되었습니다."),
    DIRECTOR_DELETE_SUCCESS(HttpStatus.OK, "감독이 성공적으로 삭제되었습니다."), // 메시지 수정
    MOVIE_DELETE_SUCCESS(HttpStatus.OK, "영화가 성공적으로 삭제되었습니다."),

    /**
     * 201 CREATED
     */
    DIRECTOR_SAVE_SUCCESS(HttpStatus.CREATED, "감독이 성공적으로 생성되었습니다."),
    MOVIE_SAVE_SUCCESS(HttpStatus.CREATED, "영화가 성공적으로 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
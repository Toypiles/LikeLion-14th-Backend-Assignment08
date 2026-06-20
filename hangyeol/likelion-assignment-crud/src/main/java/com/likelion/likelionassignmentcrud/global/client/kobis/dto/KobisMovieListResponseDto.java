package com.likelion.likelionassignmentcrud.global.client.kobis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KobisMovieListResponseDto(
        MovieListResult movieListResult,   // 정상 응답일 때 채워짐
        FaultInfo faultInfo                // 키 오류 등 비정상일 때 채워짐 (HTTP 200으로 옴!)
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieListResult(
            List<MovieListItem> movieList   // totCnt, source는 안 쓰므로 아예 안 받음
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieListItem(
            String movieCd,
            String movieNm,
            String movieNmEn,
            String prdtYear,
            String openDt,        // 따옴표 있는 문자열 → String (숫자 아님)
            String typeNm,
            String prdtStatNm,
            String nationAlt,
            String genreAlt,
            String repNationNm,
            String repGenreNm,
            List<DirectorItem> directors
            // companys 등 그 외 필드는 ignoreUnknown 덕분에 안 받아도 에러 안 남
    ) {}

    public record DirectorItem(String peopleNm) {}

    public record FaultInfo(String message, String errorCode) {}
}
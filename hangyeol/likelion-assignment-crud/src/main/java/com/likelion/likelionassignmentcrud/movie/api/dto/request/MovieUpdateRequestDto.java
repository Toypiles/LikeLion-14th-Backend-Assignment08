package com.likelion.likelionassignmentcrud.movie.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MovieUpdateRequestDto(
        @NotBlank(message = "영화 제목은 필수로 입력해야 합니다.")
        String title,

        @NotBlank(message = "장르는 필수로 입력해야 합니다.")
        String genre
) {
}
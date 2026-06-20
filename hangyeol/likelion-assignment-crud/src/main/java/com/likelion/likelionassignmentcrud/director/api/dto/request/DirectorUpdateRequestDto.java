package com.likelion.likelionassignmentcrud.director.api.dto.request;

import com.likelion.likelionassignmentcrud.director.domain.Part;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DirectorUpdateRequestDto(
        @NotBlank(message = "이름은 필수로 입력해야 합니다.")
        String name,

        @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
        @Max(value = 100, message = "나이는 100세 이하이어야 합니다.")
        int age,

        @Min(value = 1960, message = "데뷔년도는 1960년도 이상이어야 합니다.")
        @Max(value = 2026, message = "데뷔년도는 2026년도 이하이어야 합니다.")
        int debutYear,

        @NotNull(message = "파트를 필수로 입력해야 합니다.")
        Part part
) {
}
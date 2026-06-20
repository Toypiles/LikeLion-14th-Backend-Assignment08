package com.likelion.likelionassignmentcrud.director.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DirectorListResponseDto(List<DirectorInfoResponseDto> directors) {
    public static DirectorListResponseDto from(List<DirectorInfoResponseDto> directors) {
        return DirectorListResponseDto.builder()
                .directors(directors)
                .build();
    }
}

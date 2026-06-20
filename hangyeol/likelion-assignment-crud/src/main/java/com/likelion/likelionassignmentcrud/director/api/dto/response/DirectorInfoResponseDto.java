package com.likelion.likelionassignmentcrud.director.api.dto.response;

import com.likelion.likelionassignmentcrud.director.domain.Director;
import com.likelion.likelionassignmentcrud.director.domain.Part;
import lombok.Builder;

@Builder
public record DirectorInfoResponseDto(String name, int age, int debutYear, Part part) {
    public static DirectorInfoResponseDto from(Director director) {
        return DirectorInfoResponseDto.builder()
                .name(director.getName())
                .age(director.getAge())
                .debutYear(director.getDebutYear())
                .part(director.getPart())
                .build();
    }
}

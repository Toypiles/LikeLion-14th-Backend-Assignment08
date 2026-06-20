package com.likelion.likelionassignmentcrud.movie.api.dto.response;

import com.likelion.likelionassignmentcrud.movie.domain.Movie;
import lombok.Builder;

@Builder
public record MovieInfoResponseDto(String title, String genre, String directorName) {
    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .directorName(movie.getDirector().getName())
                .build();
    }
}

package com.likelion.likelionassignmentcrud.movie.api.dto.response;

import com.likelion.likelionassignmentcrud.global.client.kobis.dto.KobisMovieListResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public record MovieSearchResponseDto(
        String title,
        String genre,
        String openDate,
        List<String> directorNames,
        boolean directorAlreadyRegistered
) {
    public static MovieSearchResponseDto from(
            KobisMovieListResponseDto.MovieListItem item,
            boolean directorAlreadyRegistered
    ) {
        List<String> directorNames = item.directors() == null
                ? List.of()
                : item.directors().stream()
                  .map(KobisMovieListResponseDto.DirectorItem::peopleNm)
                  .toList();

        return MovieSearchResponseDto.builder()
                .title(item.movieNm())
                .genre(item.repGenreNm())
                .openDate(item.openDt())
                .directorNames(directorNames)
                .directorAlreadyRegistered(directorAlreadyRegistered)
                .build();
    }
}
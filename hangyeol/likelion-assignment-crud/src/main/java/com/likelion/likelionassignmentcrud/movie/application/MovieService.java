package com.likelion.likelionassignmentcrud.movie.application;

import com.likelion.likelionassignmentcrud.common.exception.BusinessException;
import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.director.domain.Director;
import com.likelion.likelionassignmentcrud.director.domain.repository.DirectorRepository;
import com.likelion.likelionassignmentcrud.global.client.kobis.KobisApiClient;
import com.likelion.likelionassignmentcrud.global.client.kobis.dto.KobisMovieListResponseDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.request.MovieSaveRequestDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.request.MovieUpdateRequestDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieInfoResponseDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieListResponseDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieSearchResponseDto;
import com.likelion.likelionassignmentcrud.movie.domain.Movie;
import com.likelion.likelionassignmentcrud.movie.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final KobisApiClient kobisApiClient;

    @Transactional
    public void movieSave(MovieSaveRequestDto movieSaveRequestDto){
        Director director = directorRepository.findById(movieSaveRequestDto.directorId()).orElseThrow(IllegalArgumentException::new);

        Movie movie = Movie.builder()
                .title(movieSaveRequestDto.title())
                .genre(movieSaveRequestDto.genre())
                .director(director)
                .build();

        movieRepository.save(movie);
    }

    public Page<MovieInfoResponseDto> movieFindDirector(Long directorId, Pageable pageable) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(()->new BusinessException(
                        ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION.getMessage()
                ));
        Page<Movie> movies = movieRepository.findByDirector(director, pageable);
        return movies.map(MovieInfoResponseDto::from);
    }

    @Transactional
    public void movieUpdate(Long movieId, MovieUpdateRequestDto movieUpdateRequestDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(IllegalArgumentException::new);
        movie.update(movieUpdateRequestDto);
    }

    @Transactional
    public void movieDelete(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(IllegalArgumentException::new);
        movieRepository.delete(movie);
    }

    public List<MovieSearchResponseDto> searchExternalMovies(String title) {
        List<KobisMovieListResponseDto.MovieListItem> items =
                kobisApiClient.searchMovieByTitle(title);

        return items.stream()
                .map(item -> {
                    boolean registered = item.directors() != null
                            && item.directors().stream()
                            .anyMatch(d -> directorRepository.existsByName(d.peopleNm()));
                    return MovieSearchResponseDto.from(item, registered);
                })
                .toList();
    }
}

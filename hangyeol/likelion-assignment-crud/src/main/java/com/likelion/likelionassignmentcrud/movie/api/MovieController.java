package com.likelion.likelionassignmentcrud.movie.api;

import com.likelion.likelionassignmentcrud.common.response.code.SuccessCode;
import com.likelion.likelionassignmentcrud.common.template.ApiResTemplate;
import com.likelion.likelionassignmentcrud.movie.api.dto.request.MovieSaveRequestDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.request.MovieUpdateRequestDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieInfoResponseDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieListResponseDto;
import com.likelion.likelionassignmentcrud.movie.api.dto.response.MovieSearchResponseDto;
import com.likelion.likelionassignmentcrud.movie.application.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
@Tag(name = "MOVIE API", description = "영화 정보를 관리하는 API입니다.")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/save") // 경로를 명시적으로 지정하거나 유지
    @Operation(summary = "영화 저장", description = "새로운 영화 정보를 저장합니다.")
    public ApiResTemplate<Void> movieSave(@RequestBody @Valid MovieSaveRequestDto movieSaveRequestDto) { // @Valid 추가
        movieService.movieSave(movieSaveRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MOVIE_SAVE_SUCCESS); // 공통 템플릿 적용
    }

    @GetMapping("/{directorId}")
    @Operation(summary = "감독별 영화 조회", description = "감독 ID를 기준으로 영화 목록을 페이징하여 조회합니다.")
    public ApiResTemplate<Page<MovieInfoResponseDto>> myMovieAll(
            @PathVariable("directorId") Long directorId,
            @ParameterObject @PageableDefault(size = 10, sort = "movieId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieInfoResponseDto> movies = movieService.movieFindDirector(directorId, pageable);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, movies);
    }

    @PatchMapping("/{movieId}")
    @Operation(summary = "영화 정보 수정", description = "영화 ID를 통해 제목과 장르를 수정합니다.")
    public ApiResTemplate<Void> movieUpdate(
            @PathVariable("movieId") Long movieId,
            @RequestBody @Valid MovieUpdateRequestDto movieUpdateRequestDto) { // @Valid 추가
        movieService.movieUpdate(movieId, movieUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MOVIE_UPDATE_SUCCESS); // 공통 템플릿 적용
    }

    @DeleteMapping("/{movieId}")
    @Operation(summary = "영화 삭제", description = "영화 ID를 통해 정보를 삭제합니다.")
    public ApiResTemplate<Void> movieDelete(@PathVariable("movieId") Long movieId) {
        movieService.movieDelete(movieId);
        return ApiResTemplate.successWithNoContent(SuccessCode.MOVIE_DELETE_SUCCESS); // 공통 템플릿 적용
    }

    @GetMapping("/search")
    @Operation(
            summary = "외부 영화 검색 (KOBIS 연동)",
            description = "영화진흥위원회(KOBIS) 공공 API로 제목을 검색하고, 해당 감독이 우리 서비스에 등록된 감독인지 함께 보여줍니다."
    )
    public ApiResTemplate<List<MovieSearchResponseDto>> movieSearchFromKobis(
            @RequestParam("title") String title) {
        List<MovieSearchResponseDto> result = movieService.searchExternalMovies(title);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, result);
    }
}
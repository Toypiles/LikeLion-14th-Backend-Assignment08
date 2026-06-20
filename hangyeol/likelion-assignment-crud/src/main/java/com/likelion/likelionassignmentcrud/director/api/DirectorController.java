package com.likelion.likelionassignmentcrud.director.api;

import com.likelion.likelionassignmentcrud.common.response.code.SuccessCode;
import com.likelion.likelionassignmentcrud.common.template.ApiResTemplate;
import com.likelion.likelionassignmentcrud.director.api.dto.request.DirectorSaveRequestDto;
import com.likelion.likelionassignmentcrud.director.api.dto.request.DirectorUpdateRequestDto;
import com.likelion.likelionassignmentcrud.director.api.dto.response.DirectorInfoResponseDto;
import com.likelion.likelionassignmentcrud.director.api.dto.response.DirectorListResponseDto;
import com.likelion.likelionassignmentcrud.director.application.DirectorService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
@Tag(name = "감독 API", description = "감독 정보를 관리하는 API입니다.")
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping("/save")
    @Operation(summary = "감독 저장", description = "새로운 감독 정보를 저장합니다.")
    public ApiResTemplate<Void> directorSave(@RequestBody @Valid DirectorSaveRequestDto directorSaveRequestDto) {
        directorService.directorSave(directorSaveRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.DIRECTOR_SAVE_SUCCESS);
    }

    @GetMapping("/all")
    @Operation(summary = "감독 전체 조회", description = "등록된 모든 감독의 목록을 조회합니다.")
    public ApiResTemplate<Page<DirectorInfoResponseDto>> directorFindAll(
            @ParameterObject
            @PageableDefault (
                    size=10,
                    sort="directorId",
                    direction = Sort.Direction.ASC
            )Pageable pageable
    ) {
        Page<DirectorInfoResponseDto> directors = directorService.directorFindAll(pageable);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, directors);
    }

    @GetMapping("/{directorId}")
    @Operation(summary = "감독 단건 조회", description = "감독 ID를 통해 특정 감독의 상세 정보를 조회합니다.")
    public ApiResTemplate<DirectorInfoResponseDto> directorFindOne(@PathVariable("directorId") Long directorId) {
        DirectorInfoResponseDto directorInfoResponseDto = directorService.directorFindOne(directorId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, directorInfoResponseDto);
    }

    @PatchMapping("/{directorId}")
    @Operation(summary = "감독 정보 수정", description = "감독 ID를 통해 기존 감독의 정보를 수정합니다.")
    public ApiResTemplate<Void> directorUpdate(@PathVariable("directorId") Long directorId, @RequestBody @Valid DirectorUpdateRequestDto directorUpdateRequestDto){
        directorService.directorUpdate(directorId, directorUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.DIRECTOR_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{directorId}")
    @Operation(summary = "감독 삭제", description = "감독 ID를 통해 감독 정보를 삭제합니다. 연관된 영화도 함께 삭제될 수 있습니다.")
    public ApiResTemplate<Void> directorDelete(@PathVariable("directorId") Long directorId) {
        directorService.directorDelete(directorId);
        return ApiResTemplate.successWithNoContent(SuccessCode.DIRECTOR_DELETE_SUCCESS);
    }
}
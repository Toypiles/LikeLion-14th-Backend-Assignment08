package com.likelion.likelionassignmentcrud.director.application;

import com.likelion.likelionassignmentcrud.common.exception.BusinessException;
import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.director.api.dto.request.DirectorSaveRequestDto;
import com.likelion.likelionassignmentcrud.director.api.dto.request.DirectorUpdateRequestDto;
import com.likelion.likelionassignmentcrud.director.api.dto.response.DirectorInfoResponseDto;
import com.likelion.likelionassignmentcrud.director.api.dto.response.DirectorListResponseDto;
import com.likelion.likelionassignmentcrud.director.domain.Director;
import com.likelion.likelionassignmentcrud.director.domain.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectorService {
    private final DirectorRepository directorRepository;

    @Transactional
    public void directorSave(DirectorSaveRequestDto directorSaveRequestDto) {
        Director director = Director.builder()
                .name(directorSaveRequestDto.name())
                .age(directorSaveRequestDto.age())
                .debutYear(directorSaveRequestDto.debutYear())
                .part(directorSaveRequestDto.part())
                .build();
        directorRepository.save(director);
    }

    public DirectorInfoResponseDto directorFindOne(Long directorId) {
        Director director = directorRepository
                .findById(directorId)
                .orElseThrow(()-> new BusinessException(ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION, ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION.getMessage() + directorId));

        return DirectorInfoResponseDto.from(director);
    }

    public Page<DirectorInfoResponseDto> directorFindAll(Pageable pageable) {

        Page<Director> directors = directorRepository.findAll(pageable);
        return directors.map(DirectorInfoResponseDto::from);
    }

    @Transactional
    public void directorUpdate(Long directorId, DirectorUpdateRequestDto directorUpdateRequestDto) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(()->new BusinessException(
                ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION,
                ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION.getMessage()+directorId));
        director.update(directorUpdateRequestDto);
    }

    @Transactional
    public void directorDelete(Long directorId) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(()->new BusinessException(
                        ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIRECTOR_NOT_FOUND_EXCEPTION.getMessage()+ directorId));
        directorRepository.delete(director);
    }
}

package com.likelion.likelionassignmentcrud.common.exception;


import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.common.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class CustomExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResTemplate<Void>> handleServerException(final Exception e) {
        log.error("Internal Server Error: {}", e.getMessage(), e);
        ApiResTemplate<Void> response = ApiResTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResTemplate<Void>> handleCustomException(BusinessException e){
        log.error("CustomException: {}", e.getMessage(), e);
        ApiResTemplate<Void> apiResponse = ApiResTemplate.errorResponse(e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResTemplate<Void>> handleValidationExceptions(MethodArgumentNotValidException e){
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(
                ApiResTemplate.errorResponse(
                        ErrorCode.VALIDATION_EXCEPTION,
                        ErrorCode.VALIDATION_EXCEPTION.getMessage() + convertMapToString(errorMap)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    private String convertMapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}



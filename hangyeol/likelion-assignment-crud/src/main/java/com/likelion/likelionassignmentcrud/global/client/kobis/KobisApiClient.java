package com.likelion.likelionassignmentcrud.global.client.kobis;

import com.likelion.likelionassignmentcrud.common.exception.BusinessException;
import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.global.client.kobis.dto.KobisMovieListResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class KobisApiClient {

    private final RestClient restClient;

    @Value("${external-api.kobis.url}")
    private String kobisApiUrl;

    @Value("${external-api.kobis.key}")
    private String kobisApiKey;

    public KobisApiClient(@Qualifier("kobisRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<KobisMovieListResponseDto.MovieListItem> searchMovieByTitle(String title) {
        try {
            // String이 아니라 URI 객체로 만들어서 RestClient가 다시 인코딩하지 않게 함 (이중 인코딩 방지)
            URI uri = UriComponentsBuilder.fromHttpUrl(kobisApiUrl)
                    .queryParam("key", kobisApiKey)
                    .queryParam("movieNm", title)
                    .build()
                    .encode()
                    .toUri();

            KobisMovieListResponseDto response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, res) -> {
                        log.error("KOBIS API 오류: {}", res.getStatusCode());
                        throw new BusinessException(
                                ErrorCode.EXTERNAL_API_ERROR,
                                ErrorCode.EXTERNAL_API_ERROR.getMessage()
                        );
                    })
                    .body(KobisMovieListResponseDto.class);

            // KOBIS는 키 오류 등에도 HTTP 200을 주고, faultInfo로 에러를 표현함
            // → onStatus로는 절대 안 걸러지므로 반드시 따로 체크해야 함
            if (response != null && response.faultInfo() != null) {
                log.error("KOBIS API 인증/요청 오류: {} ({})",
                        response.faultInfo().message(),
                        response.faultInfo().errorCode());
                throw new BusinessException(
                        ErrorCode.EXTERNAL_API_ERROR,
                        ErrorCode.EXTERNAL_API_ERROR.getMessage()
                );
            }

            if (response == null
                    || response.movieListResult() == null
                    || response.movieListResult().movieList() == null) {
                log.warn("KOBIS 검색 결과가 없습니다. title={}", title);
                return Collections.emptyList();
            }

            return response.movieListResult().movieList();

        } catch (ResourceAccessException e) {
            log.error("KOBIS 서버 연결 실패: {}", e.getMessage());
            throw new BusinessException(
                    ErrorCode.KOBIS_SERVER_ERROR,
                    ErrorCode.KOBIS_SERVER_ERROR.getMessage()
            );

        } catch (BusinessException e) {
            throw e;

        } catch (Exception e) {
            log.error("KOBIS API 예기치 않은 오류: {}", e.getMessage());
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
            );
        }
    }
}
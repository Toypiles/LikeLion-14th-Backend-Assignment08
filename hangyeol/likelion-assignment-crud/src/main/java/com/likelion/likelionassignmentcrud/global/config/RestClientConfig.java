package com.likelion.likelionassignmentcrud.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean(name = "kobisRestClient")
    public RestClient kobisRestClient() {
        return RestClient.builder().build();
    }
}
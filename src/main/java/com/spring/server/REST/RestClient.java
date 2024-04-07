package com.spring.server.REST;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class RestClient {

    private static final int TIME = 3000;

    public RestTemplate getRestClient() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder
                .setConnectTimeout(Duration.ofMillis(TIME))
                .setReadTimeout(Duration.ofMillis(TIME))
                .build();
    }
}

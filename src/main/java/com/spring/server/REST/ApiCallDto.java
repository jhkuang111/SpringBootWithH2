package com.spring.server.REST;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "forbes400.billionaire")
@Validated
public class ApiCallDto {

    Logger logger = LoggerFactory.getLogger(ApiCallDto.class);

    @NotNull
    @NotEmpty
    @URL
    @Value("${forbes400.billionaire.api}")
    private final String api;

    @Positive
    @Value("${forbes400.billionaire.maxrecord}")
    private final int maxrecord;

    public ApiCallDto(@NotNull @NotEmpty @URL String api, @Positive int maxrecord) {
        logger.info("Initiating ApiCallDto constructor");
        this.api = api;
        this.maxrecord = maxrecord;
    }

    public String getApi() {
        return api;
    }

    public int getMaxrecord() {
        return maxrecord;
    }

    public String buildEndPoint() {
        return api + "?limit=" + maxrecord;
    }
}

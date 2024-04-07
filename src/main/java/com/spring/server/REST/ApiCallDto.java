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

// @ConfigurationProperties maps to prefix, POJO fields map to other property fields
@ConfigurationProperties(prefix = "forbes400.billionaire")
@Validated
public class ApiCallDto {

    Logger logger = LoggerFactory.getLogger(ApiCallDto.class);

    @NotNull
    @NotEmpty
    @URL
    private final String api;

    @Positive
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

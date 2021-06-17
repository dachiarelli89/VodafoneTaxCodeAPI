package com.davidechiarelli.taxcodeapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class TaxCodeApiApplication {

    @Value("${app.apiTitle}")
    private String apiTitle;
    @Value("${app.apiVersion}")
    private String apiVersion;
    @Value("${app.apiDescription}")
    private String apiDescription;

    public static void main(String[] args) {

        SpringApplication.run(TaxCodeApiApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .version(apiVersion)
                        .description(apiDescription));
    }
}

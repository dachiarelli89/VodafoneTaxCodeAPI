package com.davidechiarelli.taxcodeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaxCodeApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaxCodeApiApplication.class, args);
    }
}

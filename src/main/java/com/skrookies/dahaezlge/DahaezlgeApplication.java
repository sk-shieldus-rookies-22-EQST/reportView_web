package com.skrookies.dahaezlge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DahaezlgeApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DahaezlgeApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}

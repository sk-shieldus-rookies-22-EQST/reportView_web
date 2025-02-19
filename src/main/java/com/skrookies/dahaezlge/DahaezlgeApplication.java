package com.skrookies.dahaezlge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DahaezlgeApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DahaezlgeApplication.class);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DahaezlgeApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}

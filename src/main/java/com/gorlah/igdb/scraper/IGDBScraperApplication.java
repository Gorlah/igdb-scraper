package com.gorlah.igdb.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
class IGDBScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(IGDBScraperApplication.class, args);
    }
}

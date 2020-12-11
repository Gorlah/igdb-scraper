package com.gorlah.igdb.scraper;

import com.google.common.base.Preconditions;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
class IGDBScraperApplication {

    private static final List<String> applicationParameters = List.of("scrape", "load", "delete");
    private static final String noArgumentsError = "must call with one of the following parameters: %s"
            .formatted(String.join(", ", applicationParameters));

    public static void main(String[] args) {
        Preconditions.checkState(args.length == 1, noArgumentsError);
        Preconditions.checkState(applicationParameters.contains(args[0]));
        new SpringApplicationBuilder(IGDBScraperApplication.class)
                .profiles(args[0])
                .run(args);
    }
}

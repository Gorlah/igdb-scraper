package com.gorlah.igdb.scraper.game;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Data
@Profile("scrape")
@ConfigurationProperties("igdb.game")
class GameScraperProperties {

    private List<String> fields;
    private int pageSize;
    private int threads;
}

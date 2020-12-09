package com.gorlah.igdb.scraper.game;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("igdb.game")
class GameScraperProperties {

    private List<String> fields;
    private int pageSize;
    private int threads;
}

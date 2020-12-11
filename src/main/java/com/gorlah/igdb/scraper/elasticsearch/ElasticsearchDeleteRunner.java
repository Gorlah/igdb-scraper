package com.gorlah.igdb.scraper.elasticsearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("delete")
@Component
@RequiredArgsConstructor
class ElasticsearchDeleteRunner implements ApplicationRunner {

    private final RestHighLevelClient elasticsearchClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        elasticsearchClient.indices().delete(Requests.deleteIndexRequest("games"), RequestOptions.DEFAULT);
        log.info("done!");
    }
}

package com.gorlah.igdb.scraper.game;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.utils.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import proto.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Slf4j
@Component
@RequiredArgsConstructor
class GameScraperRunner implements ApplicationRunner {

    private final IGDBWrapper igdbWrapper;
    private final ObjectMapper objectMapper;
    private final GameScraperProperties gameScraperProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Files.createDirectories(Paths.get("./.igdb"));
        var maxId = queryForMaxId();
        int pages = maxId / gameScraperProperties.getPageSize();
        new ForkJoinPool(gameScraperProperties.getThreads()).submit(() ->
                IntStream.range(0, pages)
                        .parallel()
                        .forEach(this::processPage))
                .get();
    }

    private int queryForMaxId() throws RequestException, JsonProcessingException {
        var request = new APICalypse()
                .fields("id")
                .sort("id", Sort.DESCENDING)
                .limit(1);
        var response = igdbWrapper.apiJsonRequest(Endpoints.GAMES, request.buildQuery());
        var responseTree = objectMapper.readTree(response);
        return StreamSupport.stream(responseTree.spliterator(), false)
                .findFirst()
                .map(id -> id.get("id").asInt())
                .orElseThrow(IllegalStateException::new);
    }

    private void processPage(int page) {
        try {
            var games = queryForPage(page);
            log.info("page = %d, begin = %d, end = %d, results = %d"
                    .formatted(page, page * gameScraperProperties.getPageSize(), (page + 1) * gameScraperProperties.getPageSize(), games.size()));
            objectMapper.writer().writeValue(new File("./.igdb/games.%d.json".formatted(page)), games);
        } catch (RequestException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<JsonNode> queryForPage(int page) throws RequestException, JsonProcessingException {
        var request = new APICalypse()
                .fields(String.join(",", gameScraperProperties.getFields()))
                .where(buildWhere(page))
                .sort("id", Sort.ASCENDING)
                .limit(gameScraperProperties.getPageSize());
        var response = igdbWrapper.apiJsonRequest(Endpoints.GAMES, request.buildQuery());
        var responseTree = objectMapper.readTree(response);
        return StreamSupport.stream(responseTree.spliterator(), false)
                .map(this::expandGame)
                .collect(toImmutableList());
    }

    private JsonNode expandGame(JsonNode gameNode) {
        replaceNode(gameNode, "category", (game, field) -> GameCategoryEnum.forNumber(game.get(field).asInt()).toString());
        replaceNode(gameNode, "status", (game, field) -> GameStatusEnum.forNumber(game.get(field).asInt()).toString());
        if (gameNode.has("age_ratings")) {
            var ageRatings = gameNode.get("age_ratings");
            replaceAllNodes(ageRatings, "category", (ageRating, field) -> AgeRatingCategoryEnum.forNumber(ageRating.get(field).asInt()).toString());
            replaceAllNodes(ageRatings, "rating", (ageRating, field) -> AgeRatingRatingEnum.forNumber(ageRating.get(field).asInt()).toString());
        }
        if (gameNode.has("external_games")) {
            var externalGames = gameNode.get("external_games");
            replaceAllNodes(externalGames, "category", (externalGame, field) -> GameExternalCategory.fromValue(externalGame.get(field).asInt()).toString());
            replaceAllNodes(externalGames, "media", (externalGame, field) -> ExternalGameMediaEnum.forNumber(externalGame.get(field).asInt()).toString());
        }
        if (gameNode.has("platforms")) {
            var platforms = gameNode.get("platforms");
            replaceAllNodes(platforms, "category", (platform, field) -> PlatformCategoryEnum.forNumber(platform.get(field).asInt()).toString());
        }
        if (gameNode.has("release_dates")) {
            var releaseDates = gameNode.get("release_dates");
            replaceAllNodes(releaseDates, "category", (releaseDate, field) -> GameReleaseDateCategory.fromValue(releaseDate.get(field).asInt()).toString());
            replaceAllNodes(releaseDates, "region", (releaseDate, field) -> GameRegion.fromValue(releaseDate.get(field).asInt()).toString());
        }
        if (gameNode.has("websites")) {
            var websites = gameNode.get("websites");
            replaceAllNodes(websites, "category", (website, field) -> GameWebsite.fromValue(website.get(field).asInt()).toString());
        }
        return gameNode;
    }

    private void replaceNode(JsonNode jsonNode, String fieldName, BiFunction<JsonNode, String, String> transformFunction) {
        if (jsonNode.has(fieldName)) {
            ((ObjectNode) jsonNode).put(fieldName, transformFunction.apply(jsonNode, fieldName));
        }
    }

    private void replaceAllNodes(JsonNode jsonNode, String fieldName, BiFunction<JsonNode, String, String> transformFunction) {
        StreamSupport.stream(jsonNode.spliterator(), false)
                .filter(arrayNode -> arrayNode.has(fieldName))
                .forEach(arrayNode -> ((ObjectNode) arrayNode).put(fieldName, transformFunction.apply(arrayNode, fieldName)));
    }

    private String buildWhere(int page) {
        return "id >= %d & id < %d".formatted(page * gameScraperProperties.getPageSize(), (page + 1) * gameScraperProperties.getPageSize());
    }
}

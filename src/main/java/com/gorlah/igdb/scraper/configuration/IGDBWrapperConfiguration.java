package com.gorlah.igdb.scraper.configuration;

import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.TwitchAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

@Configuration
class IGDBWrapperConfiguration {

    @Bean
    IGDBWrapper igdbWrapper(@Value("${TWITCH_CLIENT_ID}") String clientId,
                            @Value("${TWITCH_CLIENT_SECRET}") String clientSecret) {
        requireNonNull(clientId);
        requireNonNull(clientSecret);
        var twitchAuthenticator = TwitchAuthenticator.INSTANCE;
        var igdbClient = IGDBWrapper.INSTANCE;
        var twitchToken = twitchAuthenticator.requestTwitchToken(clientId, clientSecret);
        var accessToken = requireNonNull(twitchToken).getAccess_token();
        igdbClient.setCredentials(clientId, accessToken);
        return igdbClient;
    }
}

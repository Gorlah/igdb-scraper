package com.gorlah.igdb.scraper.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum GameWebsite {
    OFFICIAL(1),
    WIKIA(2),
    WIKIPEDIA(3),
    FACEBOOK(4),
    TWITTER(5),
    TWITCH(6),
    INSTAGRAM(8),
    YOUTUBE(9),
    IPHONE(10),
    IPAD(11),
    ANDROID(12),
    STEAM(13),
    REDDIT(14),
    ITCH(15),
    EPIC_GAMES(16),
    GOG(17),
    DISCORD(18);
    
    private final int value;

    @SuppressWarnings("DuplicatedCode")
    public static GameWebsite fromValue(int value) {
        return switch (value) {
            case 1 -> OFFICIAL;
            case 2 -> WIKIA;
            case 3 -> WIKIPEDIA;
            case 4 -> FACEBOOK;
            case 5 -> TWITTER;
            case 6 -> TWITCH;
            case 8 -> INSTAGRAM;
            case 9 -> YOUTUBE;
            case 10 -> IPHONE;
            case 11 -> IPAD;
            case 12 -> ANDROID;
            case 13 -> STEAM;
            case 14 -> REDDIT;
            case 15 -> ITCH;
            case 16 -> EPIC_GAMES;
            case 17-> GOG;
            case 18 -> DISCORD;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}

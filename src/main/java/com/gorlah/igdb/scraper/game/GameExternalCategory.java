package com.gorlah.igdb.scraper.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameExternalCategory {
    STEAM(1),
    THE_GAMES_DB(2),
    GIANT_BOMB(3),
    CO_OPTIMUS(4),
    GOG(5),
    PUSH_SQUARE(6),
    IS_THERE_ANY_DEAL(7),
    GAMERS_GATE(8),
    YOUTUBE(10),
    MICROSOFT(11),
    NINTENDO_LIFE(12),
    APPLE(13),
    TWITCH(14),
    ANDROID(15),
    PLAYSTATION(16),
    GAMES_PRESS(19),
    AMAZON(20);

    private final int value;

    @SuppressWarnings("DuplicatedCode")
    public static GameExternalCategory fromValue(int value) {
        return switch (value) {
            case 1 -> STEAM;
            case 2 -> THE_GAMES_DB;
            case 3 -> GIANT_BOMB;
            case 4 -> CO_OPTIMUS;
            case 5 -> GOG;
            case 6 -> PUSH_SQUARE;
            case 7 -> IS_THERE_ANY_DEAL;
            case 8 -> GAMERS_GATE;
            case 10 -> YOUTUBE;
            case 11, 18 -> MICROSOFT;
            case 12 -> NINTENDO_LIFE;
            case 13 -> APPLE;
            case 14 -> TWITCH;
            case 15 -> ANDROID;
            case 16 -> PLAYSTATION;
            case 19 -> GAMES_PRESS;
            case 20 -> AMAZON;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}

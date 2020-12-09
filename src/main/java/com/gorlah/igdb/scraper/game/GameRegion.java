package com.gorlah.igdb.scraper.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameRegion {
    UNKNOWN(-1),
    EUROPE(1),
    NORTH_AMERICA(2),
    AUSTRALIA(3),
    NEW_ZEALAND(4),
    JAPAN(5),
    CHINA(6),
    ASIA(7),
    WORLDWIDE(8);

    private final int value;

    @SuppressWarnings("DuplicatedCode")
    public static GameRegion fromValue(int value) {
        return switch (value) {
            case 1 -> EUROPE;
            case 2 -> NORTH_AMERICA;
            case 3 -> AUSTRALIA;
            case 4 -> NEW_ZEALAND;
            case 5 -> JAPAN;
            case 6 -> CHINA;
            case 7 -> ASIA;
            case 8 -> WORLDWIDE;
            default -> UNKNOWN;
        };
    }
}

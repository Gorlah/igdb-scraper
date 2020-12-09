package com.gorlah.igdb.scraper.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameReleaseDateCategory {
    YYYYMMMMDD(0),
    YYYYMMMM(1),
    YYYY(2),
    YYYYQ1(3),
    YYYYQ2(4),
    YYYYQ3(5),
    YYYYQ4(6),
    TBD(7);

    private final int value;

    @SuppressWarnings("DuplicatedCode")
    public static GameReleaseDateCategory fromValue(int value) {
        return switch (value) {
            case 0 -> YYYYMMMMDD;
            case 1 -> YYYYMMMM;
            case 2 -> YYYY;
            case 3 -> YYYYQ1;
            case 4 -> YYYYQ2;
            case 5 -> YYYYQ3;
            case 6 -> YYYYQ4;
            case 7 -> TBD;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}

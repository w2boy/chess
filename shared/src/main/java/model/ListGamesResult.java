package model;

import java.util.ArrayList;

public record ListGamesResult(
        String message,
        ArrayList<GameData> games) {}

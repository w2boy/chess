package service;

import model.GameData;

public record ListGamesResult(
        String message,
        GameData[] listOfGameData) {}

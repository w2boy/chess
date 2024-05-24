package service;

import model.GameData;

public record CreateGameResult(
        String message,
        Integer gameID) {}

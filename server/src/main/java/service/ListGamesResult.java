package service;

import model.GameData;

import java.util.ArrayList;

public record ListGamesResult(
        String message,
        ArrayList<GameData> games) {}

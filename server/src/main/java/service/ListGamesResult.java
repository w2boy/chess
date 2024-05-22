package service;

import model.GameData;

record ListGamesResult(
        GameData[] listOfGameData) {}

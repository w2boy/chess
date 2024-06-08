package ui;

public record ListGamesOutput (
    int gameID,
    String whiteUsername,
    String blackUsername,
    String gameName
) {}

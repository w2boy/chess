package service;

public record LoginResult(
        String message,
        String username,
        String authToken) {}

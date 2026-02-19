package ch.bumfuzzle.auth;

public record LoginRequest(
    String username,
    String password
) {
}

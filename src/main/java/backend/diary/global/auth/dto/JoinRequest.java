package backend.diary.global.auth.dto;

public record JoinRequest(
    String username,
    String password,
    String nickname
) { }

package backend.diary.domain.follow.dto;

public record FollowUserInfoDTO(
        Long userId,
        String nickname,
        boolean isFollow,
        boolean isSelf
) {
}

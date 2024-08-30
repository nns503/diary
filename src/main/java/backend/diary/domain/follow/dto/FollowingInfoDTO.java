package backend.diary.domain.follow.dto;

public record FollowingInfoDTO(
        Long userId,
        String nickname,
        boolean isFollow,
        boolean isSelf
) {
}

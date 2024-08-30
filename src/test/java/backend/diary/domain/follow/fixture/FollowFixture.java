package backend.diary.domain.follow.fixture;

import backend.diary.domain.follow.entity.Follow;
import backend.diary.domain.user.entity.User;

@SuppressWarnings("NonAsciiCharacters")
public class FollowFixture {

    public Follow 팔로우1(User user1, User user2){
        return Follow.builder()
                .id(1L)
                .follower(user1)
                .following(user2)
                .build();
    }

    public Follow 팔로우2(User user1, User user2){
        return Follow.builder()
                .id(2L)
                .follower(user1)
                .following(user2)
                .build();
    }
}

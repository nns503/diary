package backend.diary.domain.follow.service;

import backend.diary.domain.follow.entity.Follow;
import backend.diary.domain.follow.entity.repository.FollowRepository;
import backend.diary.domain.follow.exception.SelfFollowException;
import backend.diary.domain.follow.fixture.FollowFixture;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;
    @Mock
    private UserRepository userRepository;

    private final CommonUserFixture userFixture = new CommonUserFixture();
    private final FollowFixture followFixture = new FollowFixture();

    private User 일반회원1;
    private User 일반회원2;
    private Follow 팔로우1;

    @BeforeEach
    void setup(){
        일반회원1 = userFixture.일반회원1;
        일반회원2 = userFixture.일반회원2;
        팔로우1 = followFixture.팔로우1(일반회원1, 일반회원2);
    }

    @Test
    void 팔로우_성공(){
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(일반회원2));
        given(followRepository.existsByFollowerIdAndFollowingId(anyLong(), anyLong()))
                .willReturn(false);
        given(followRepository.save(any(Follow.class)))
                .willReturn(팔로우1);

        followService.addFollow(일반회원1, 일반회원2.getId());
    }

    @Test
    void 팔로우_실패_자신을_팔로우(){
        assertThrows(SelfFollowException.class,
                () -> followService.addFollow(일반회원1, 일반회원1.getId()));
    }

    @Test
    void 언팔로우_성공(){
        given(userRepository.existsById(anyLong()))
                .willReturn(true);
        given(followRepository.findByFollowerIdAndFollowingId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(팔로우1));
//        willDoNothing().given(followRepository).delete(any(Follow.class));

        followService.deleteFollow(일반회원1, 일반회원2.getId());
    }

    @Test
    void 언팔로우_실패_자신을_언팔로우(){
        assertThrows(SelfFollowException.class,
                () -> followService.deleteFollow(일반회원1, 일반회원1.getId()));
    }

}
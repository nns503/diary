package backend.diary.domain.user.service;

import backend.diary.domain.user.dto.request.UpdateNicknameRequest;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.fixture.CommonUserFixture;
import backend.diary.global.auth.exception.DuplicationNicknameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final CommonUserFixture commonUserFixture = new CommonUserFixture();

    private User 일반회원1;

    @BeforeEach
    void setup(){
        일반회원1 = commonUserFixture.일반회원1;
    }

    @Test
    void 닉네임_변경_성공(){
        String updateNickname = "수정";
        UpdateNicknameRequest request = new UpdateNicknameRequest(updateNickname);

        userService.updateNickname(일반회원1, request);

        assertThat(일반회원1.getNickname()).isEqualTo(updateNickname);
    }

    @Test
    void 닉네임_변경_실패_현재_닉네임_동일(){
        String updateNickname = 일반회원1.getNickname();
        UpdateNicknameRequest request = new UpdateNicknameRequest(updateNickname);
        assertThrows(DuplicationNicknameException.class,
                () ->
                        userService.updateNickname(일반회원1, request));
    }

    @Test
    void 닉네임_변경_실패_이미_존재하는_닉네임(){
        String updateNickname = "수정";
        UpdateNicknameRequest request = new UpdateNicknameRequest(updateNickname);
        given(userRepository.existsByNickname(anyString()))
                .willReturn(true);

        assertThrows(DuplicationNicknameException.class,
                () ->
                        userService.updateNickname(일반회원1, request));
    }
}
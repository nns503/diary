package backend.diary.global.auth.service;

import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.UserRole;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.global.auth.dto.JoinRequest;
import backend.diary.global.auth.exception.DuplicationNicknameException;
import backend.diary.global.auth.exception.DuplicationUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void joinUser(JoinRequest joinRequest) {
        String username = joinRequest.username();
        String password = joinRequest.password();
        String nickname = joinRequest.nickname();

        checkDuplicationUsername(username);
        checkDuplicationNickname(nickname);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    private void checkDuplicationUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new DuplicationUsernameException();
        }
    }

    private void checkDuplicationNickname(String nickname) {
        if(userRepository.existsByNickname(nickname)){
            throw new DuplicationNicknameException();
        }
    }
}

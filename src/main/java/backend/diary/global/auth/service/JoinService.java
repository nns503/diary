package backend.diary.global.auth.service;

import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.UserRole;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.global.auth.dto.JoinRequest;
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

        CheckDuplicationUsername(username);
        CheckDuplicationNickname(nickname);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(UserRole.ROLE_ADMIN)
                .build();

        userRepository.save(user);
    }

    private void CheckDuplicationUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new RuntimeException();
        }
    }

    private void CheckDuplicationNickname(String nickname) {
        if(userRepository.existsByNickname(nickname)){
            throw new RuntimeException();
        }
    }
}

package backend.diary.domain.user.service;

import backend.diary.domain.user.dto.request.UpdateNicknameRequest;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.global.auth.exception.DuplicationNicknameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateNickname(User user, UpdateNicknameRequest request){
        String updatedNickname = request.nickname();

        validateCurrentNickname(user.getNickname(), updatedNickname);
        checkDuplicationNickname(updatedNickname);

        user.updateNickname(updatedNickname);
        userRepository.save(user);
    }

    private void checkDuplicationNickname(String updatedNickname) {
        if(userRepository.existsByNickname(updatedNickname)){
            throw new DuplicationNicknameException();
        }
    }

    private void validateCurrentNickname(String nickname, String updatedNickname) {
        if(nickname.equals(updatedNickname)) {
            throw new DuplicationNicknameException("현재 닉네임과 동일한 닉네임으로는 변경하실 수 없습니다.");
        }
    }
}

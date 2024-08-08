package backend.diary.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateNicknameRequest(
        @NotEmpty(message = "변경할 닉네임을 작성해주세요")
        @Size(min = 1, max = 10, message = "닉네임은 1자에서 10자 사이로 작성해주세요")
        String nickname
) {
}

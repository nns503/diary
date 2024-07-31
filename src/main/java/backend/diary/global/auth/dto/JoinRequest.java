package backend.diary.global.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record JoinRequest(
    @Email(message = "이메일 형식으로 작성해야합니다.")
    @NotBlank(message = "이메일을 입력해주세요")
    String username,

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 15, message = "길이는 8자에서 15자 이내로 작성해주세요")
    String password,

    @NotBlank(message = "닉네임을 입력해주세요")
    @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣 ]*$", message = "한글, 영문, 숫자만 사용 가능합니다.")
    @Size(min = 4, max = 15, message = "길이는 4자에서 15자 이내로 작성해주세요")
    String nickname
) { }

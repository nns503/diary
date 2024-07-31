package backend.diary.global.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDTO {
    String username;
    String password;
}

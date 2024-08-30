package backend.diary.domain.user.controller;

import backend.diary.domain.user.dto.request.UpdateNicknameRequest;
import backend.diary.domain.user.entity.CustomUserDetails;
import backend.diary.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<String> updateNickname(
            @Validated @RequestBody UpdateNicknameRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        userService.updateNickname(userDetails.getUser(), request);
        return ResponseEntity.ok("닉네임을 변경했습니다.");
    }
}

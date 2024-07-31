package backend.diary.global.auth.controller;

import backend.diary.global.auth.dto.JoinRequest;
import backend.diary.global.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> join(JoinRequest joinRequest) {
        joinService.joinUser(joinRequest);

        return ResponseEntity
                .ok("회원 가입에 성공하였습니다");
    }
}

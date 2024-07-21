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
    public ResponseEntity<Void> join(JoinRequest joinRequest) {
        joinService.joinUser(joinRequest);

        return ResponseEntity
                .ok()
                .build();
    }
}

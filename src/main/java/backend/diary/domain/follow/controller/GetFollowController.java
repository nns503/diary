package backend.diary.domain.follow.controller;

import backend.diary.domain.follow.dto.response.GetFollowerListResponse;
import backend.diary.domain.follow.dto.response.GetFollowingListResponse;
import backend.diary.domain.follow.service.GetFollowService;
import backend.diary.domain.user.entity.CustomUserDetails;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api")
public class GetFollowController {

    private final GetFollowService getFollowService;

    @GetMapping("/following/{findUserId}")
    public ResponseEntity<GetFollowingListResponse> getFollowingUser(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long findUserId,
        @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.") int page
    ){
        PageRequest pageable = PageRequest.of(page - 1, 10);
        GetFollowingListResponse response = getFollowService.getFollowingUser(userDetails.getUser(), findUserId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/follower/{findUserId}")
    public ResponseEntity<GetFollowerListResponse> getFollowerUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long findUserId,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.") int page
            ){
        PageRequest pageable = PageRequest.of(page - 1, 10);
        GetFollowerListResponse response = getFollowService.getFollowerUser(customUserDetails.getUser(), findUserId, pageable);
        return ResponseEntity.ok(response);

    }
}

package backend.diary.domain.follow.controller;


import backend.diary.domain.follow.service.FollowService;
import backend.diary.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public ResponseEntity<String> addFollow(
            @PathVariable Long followingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        followService.addFollow(userDetails.getUser(), followingId);

        return ResponseEntity.ok("팔로우 했습니다.");
    }

    @DeleteMapping("/{followingId}")
    public ResponseEntity<String> deleteFollow(
            @PathVariable Long followingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        followService.deleteFollow(userDetails.getUser(), followingId);

        return ResponseEntity.ok("언팔로우 했습니다.");
    }
}

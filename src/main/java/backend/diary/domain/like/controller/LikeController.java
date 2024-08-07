package backend.diary.domain.like.controller;

import backend.diary.domain.like.service.LikeService;
import backend.diary.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like/{articleId}")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likeToArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        likeService.createLike(userDetails.getUser(), articleId);

        return ResponseEntity.ok("좋아요를 눌렀습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> unlikeToArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        likeService.deleteLike(userDetails.getUser(), articleId);
        return ResponseEntity.ok("좋아요를 취소하였습니다.");
    }
}

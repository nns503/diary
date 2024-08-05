package backend.diary.domain.comment.controller;

import backend.diary.domain.comment.dto.request.CreateCommentRequest;
import backend.diary.domain.comment.dto.request.UpdateCommentRequest;
import backend.diary.domain.comment.dto.response.CreateCommentResponse;
import backend.diary.domain.comment.dto.response.UpdateCommentResponse;
import backend.diary.domain.comment.service.CommentService;
import backend.diary.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/{articleId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(
            @Validated @RequestBody CreateCommentRequest request,
            @PathVariable Long articleId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CreateCommentResponse response = commentService.createComment(request, articleId, userDetails.getUser().getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getUser().getId());
        return ResponseEntity.ok("댓글을 삭제했습니다.");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @Validated @RequestBody UpdateCommentRequest request,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        UpdateCommentResponse response = commentService.updateComment(request, commentId, userDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }
}

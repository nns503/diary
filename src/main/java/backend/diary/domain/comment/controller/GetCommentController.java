package backend.diary.domain.comment.controller;

import backend.diary.domain.comment.dto.request.GetCommentListRequest;
import backend.diary.domain.comment.dto.response.GetCommentListResponse;
import backend.diary.domain.comment.service.GetCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/{articleId}/comment")
public class GetCommentController {

    private final GetCommentService getCommentService;

    @GetMapping
    public ResponseEntity<GetCommentListResponse> getCommentList(
            @PathVariable Long articleId,
            @RequestBody GetCommentListRequest getCommentListRequest
    ){
        PageRequest pageable = PageRequest.of(getCommentListRequest.page() - 1, getCommentListRequest.size());
        GetCommentListResponse response = getCommentService.getCommentList(articleId, pageable);
        return ResponseEntity.ok(response);
    }

}

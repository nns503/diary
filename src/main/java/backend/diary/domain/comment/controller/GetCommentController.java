package backend.diary.domain.comment.controller;

import backend.diary.domain.comment.dto.response.GetCommentListResponse;
import backend.diary.domain.comment.service.GetCommentService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/{articleId}/comment")
public class GetCommentController {

    private final GetCommentService getCommentService;

    @GetMapping
    public ResponseEntity<GetCommentListResponse> getCommentList(
            @PathVariable Long articleId,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
            int page,
            @RequestParam(defaultValue = "10")  @Min(value = 1, message = "페이지 사이즈는 1 이상이어야 합니다.")
            int size
    ){
        PageRequest pageable = PageRequest.of(page - 1, size);
        GetCommentListResponse response = getCommentService.getCommentList(articleId, pageable);
        return ResponseEntity.ok(response);
    }

}

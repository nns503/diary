package backend.diary.domain.like.controller;

import backend.diary.domain.like.dto.response.GetLikeArticleListResponse;
import backend.diary.domain.like.service.GetLikeService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like/{userId}")
public class GetLikeController {

    private final GetLikeService getLikeService;

    @GetMapping
    public ResponseEntity<GetLikeArticleListResponse> getLike(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
            int page,
            @RequestParam(defaultValue = "10")  @Min(value = 1, message = "페이지 사이즈는 1 이상이어야 합니다.")
            int size
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        GetLikeArticleListResponse response = getLikeService.getLikeArticles(pageable, userId);
        return ResponseEntity.ok(response);
    }
}

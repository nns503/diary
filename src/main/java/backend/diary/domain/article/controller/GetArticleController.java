package backend.diary.domain.article.controller;

import backend.diary.domain.article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.article.dto.response.GetArticleListResponse;
import backend.diary.domain.article.service.GetArticleService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class GetArticleController {

    private final GetArticleService getArticleService;

    @GetMapping
    public ResponseEntity<GetArticleListResponse> getArticleList(
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
            int page,
            @RequestParam(name = "size", defaultValue = "10")  @Min(value = 1, message = "페이지 사이즈는 1 이상이어야 합니다.")
            int size,
            @RequestParam(name = "keywordType", required = false)
            String keywordType,
            @RequestParam(name = "keyword", required = false)
            String keyword,
            @RequestParam(name = "sort", required = false)
            String sort
    ){
        Pageable pageable = PageRequest.of(page-1, size);
        GetArticleListResponse response = getArticleService.getArticleList(pageable, keywordType, keyword, sort);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<GetArticleDetailResponse> getArticle(
            @PathVariable Long articleId
    ){
        GetArticleDetailResponse response = getArticleService.getArticle(articleId);
        return ResponseEntity.ok(response);
    }
}

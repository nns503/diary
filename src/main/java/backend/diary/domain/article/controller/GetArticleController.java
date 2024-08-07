package backend.diary.domain.article.controller;

import backend.diary.domain.article.dto.request.GetArticleListRequest;
import backend.diary.domain.article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.article.dto.response.GetArticleListResponse;
import backend.diary.domain.article.service.GetArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class GetArticleController {

    private final GetArticleService getArticleService;

    @GetMapping
    public ResponseEntity<GetArticleListResponse> getArticleList(
            @Validated @RequestBody GetArticleListRequest getArticleListRequest
    ){
        Pageable pageable = PageRequest.of(getArticleListRequest.page()-1, getArticleListRequest.size());
        GetArticleListResponse response = getArticleService.getArticleList(pageable);
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

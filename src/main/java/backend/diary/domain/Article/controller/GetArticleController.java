package backend.diary.domain.Article.controller;

import backend.diary.domain.Article.dto.request.GetArticleListRequest;
import backend.diary.domain.Article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.Article.dto.response.GetArticleListResponse;
import backend.diary.domain.Article.service.GetArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class GetArticleController {

    private final GetArticleService getArticleService;

    @GetMapping
    public ResponseEntity<GetArticleListResponse> getArticleList(
            @RequestBody GetArticleListRequest getArticleListRequest
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

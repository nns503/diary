package backend.diary.domain.community.controller;

import backend.diary.domain.community.dto.*;
import backend.diary.domain.community.service.ArticleService;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<CreateArticleResponse> createArticle(
            @RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal User user
            ) {

        CreateArticleResponse response = articleService.createArticle(request, user.getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal User user
    ){
        articleService.deleteArticle(articleId, user.getId());
        return ResponseEntity.ok("게시글을 삭제했습니다.");
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<UpdateArticleResponse> updateArticle(
            @PathVariable Long articleId,
            @RequestBody UpdateArticleRequest request,
            @AuthenticationPrincipal User user
    ){
        UpdateArticleResponse response = articleService.updateArticle(articleId, request, user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetArticlesResponse>> getArticles(
    ){
        List<GetArticlesResponse> response = articleService.getArticles();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<GetArticleResponse> getArticle(
            @PathVariable Long articleId
    ){
        GetArticleResponse response = articleService.getArticle(articleId);
        return ResponseEntity.ok(response);
    }
}

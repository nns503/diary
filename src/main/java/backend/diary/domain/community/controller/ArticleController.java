package backend.diary.domain.community.controller;

import backend.diary.domain.community.dto.CreateArticleRequest;
import backend.diary.domain.community.dto.CreateArticleResponse;
import backend.diary.domain.community.dto.UpdateArticleRequest;
import backend.diary.domain.community.dto.UpdateArticleResponse;
import backend.diary.domain.community.service.ArticleService;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}

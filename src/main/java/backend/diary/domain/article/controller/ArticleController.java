package backend.diary.domain.article.controller;

import backend.diary.domain.article.dto.request.CreateArticleRequest;
import backend.diary.domain.article.dto.response.CreateArticleResponse;
import backend.diary.domain.article.dto.request.UpdateArticleRequest;
import backend.diary.domain.article.dto.response.UpdateArticleResponse;
import backend.diary.domain.article.service.ArticleService;
import backend.diary.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<CreateArticleResponse> createArticle(
            @Validated @RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {

        CreateArticleResponse response = articleService.createArticle(request, userDetails.getUser().getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        articleService.deleteArticle(articleId, userDetails.getUser().getId());
        return ResponseEntity.ok("게시글을 삭제했습니다.");
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<UpdateArticleResponse> updateArticle(
            @PathVariable Long articleId,
            @Validated @RequestBody UpdateArticleRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        UpdateArticleResponse response = articleService.updateArticle(articleId, request, userDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }
}

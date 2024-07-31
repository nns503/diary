package backend.diary.domain.community.controller;

import backend.diary.domain.community.dto.response.GetArticleResponse;
import backend.diary.domain.community.dto.response.GetArticlesResponse;
import backend.diary.domain.community.service.GetArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class GetArticleController {

    private final GetArticleService getArticleService;

    @GetMapping
    public ResponseEntity<List<GetArticlesResponse>> getArticles(
    ){
        List<GetArticlesResponse> response = getArticleService.getArticles();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<GetArticleResponse> getArticle(
            @PathVariable Long articleId
    ){
        GetArticleResponse response = getArticleService.getArticle(articleId);
        return ResponseEntity.ok(response);
    }
}

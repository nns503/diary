package backend.diary.domain.Article.dto.response;

import backend.diary.domain.Article.entity.Article;

import java.time.LocalDateTime;

public record GetArticlesResponse(
        String title,
        String content,
        String author,
        LocalDateTime createAt
) {
    public static GetArticlesResponse convertTo(Article article) {
        return new GetArticlesResponse(
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getCreatedAt()
        );
    }
}

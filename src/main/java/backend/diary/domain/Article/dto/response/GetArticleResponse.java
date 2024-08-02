package backend.diary.domain.Article.dto.response;

import backend.diary.domain.Article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleResponse(
        long id,
        String title,
        String content,
        String author,
        LocalDateTime createAt
) {
    public static GetArticleResponse convertTo(Article article) {
        return new GetArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getCreatedAt()
        );
    }
}

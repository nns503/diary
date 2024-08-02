package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

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

package backend.diary.domain.community.dto.response;

import backend.diary.domain.community.entity.Article;

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

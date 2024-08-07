package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleDTO(
        long id,
        String title,
        String author,
        String content,
        LocalDateTime createAt
) {
    public static GetArticleDTO toConvert(Article article) {
        return new GetArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getUser().getNickname(),
                article.getContent(),
                article.getCreatedAt()
        );
    }
}

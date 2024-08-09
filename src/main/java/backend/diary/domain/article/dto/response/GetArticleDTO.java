package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleDTO(
        long id,
        String title,
        String author,
        LocalDateTime createAt,
        int viewCount,
        int likeCount
) {
    public static GetArticleDTO of(Article article) {
        return new GetArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getUser().getNickname(),
                article.getCreatedAt(),
                article.getViewCount(),
                article.getLikeCount()
        );
    }
}

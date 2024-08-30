package backend.diary.domain.like.dto.response;

import backend.diary.domain.article.entity.Article;

import java.time.LocalDateTime;

public record GetLikeArticleDTO(
        long id,
        String title,
        String author,
        LocalDateTime createAt,
        int viewCount,
        int likeCount
) {
    public static GetLikeArticleDTO of(Article article) {
        return new GetLikeArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getUser().getNickname(),
                article.getCreatedAt(),
                article.getViewCount(),
                article.getLikeCount()
        );
    }
}

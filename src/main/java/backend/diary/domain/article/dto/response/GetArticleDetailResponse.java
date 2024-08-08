package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleDetailResponse(
        String title,
        String author,
        int likeCount,
        int viewCount,
        String content,
        LocalDateTime createAt
){
    public static GetArticleDetailResponse of(Article article) {
        return new GetArticleDetailResponse(
                article.getTitle(),
                article.getUser().getNickname(),
                article.getLikeCount(),
                article.getViewCount(),
                article.getContent(),
                article.getCreatedAt()
        );
    }
}

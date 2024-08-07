package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleDetailResponse(
        String title,
        String author,
        String content,
        LocalDateTime createAt
){
    public static GetArticleDetailResponse of(Article article) {
        return new GetArticleDetailResponse(
                article.getTitle(),
                article.getUser().getNickname(),
                article.getContent(),
                article.getCreatedAt()
        );
    }
}

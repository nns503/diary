package backend.diary.domain.community.dto;

import backend.diary.domain.community.entity.Article;
import backend.diary.domain.user.entity.User;

import java.time.LocalDateTime;

public record GetArticleResponse (
        String title,
        String author,
        String content,
        String filePath,
        LocalDateTime createAt
){
    public static GetArticleResponse convertTo(Article article) {
        return new GetArticleResponse(
                article.getTitle(),
                article.getAuthor(),
                article.getContent(),
                article.getFilePath(),
                article.getCreatedAt()
        );
    }
}

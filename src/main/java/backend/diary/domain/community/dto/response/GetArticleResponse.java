package backend.diary.domain.community.dto.response;

import backend.diary.domain.community.entity.Article;

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

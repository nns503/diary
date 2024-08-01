package backend.diary.domain.Article.dto.response;

import backend.diary.domain.Article.entity.Article;

import java.time.LocalDateTime;

public record GetArticleDetailResponse(
        String title,
        String author,
        String content,
        String filePath,
        LocalDateTime createAt
){
    public static GetArticleDetailResponse convertTo(Article article) {
        return new GetArticleDetailResponse(
                article.getTitle(),
                article.getAuthor(),
                article.getContent(),
                article.getFilePath(),
                article.getCreatedAt()
        );
    }
}

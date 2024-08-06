package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

public record CreateArticleResponse(
        Long articleId,
        String title,
        String content,
        String filePath
) {
    public static CreateArticleResponse of(Article article) {
        return new CreateArticleResponse(article.getId(), article.getTitle(), article.getContent(), article.getFilePath());
    }
}

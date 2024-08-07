package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;

public record UpdateArticleResponse(
        long articleId,
        String title,
        String content
) {
    public static UpdateArticleResponse of(Article article) {
        return new UpdateArticleResponse(article.getId(), article.getTitle(), article.getContent());
    }
}

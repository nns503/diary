package backend.diary.domain.Article.dto.response;

public record UpdateArticleResponse(
        long articleId,
        String title,
        String content,
        String filePath
) {
}

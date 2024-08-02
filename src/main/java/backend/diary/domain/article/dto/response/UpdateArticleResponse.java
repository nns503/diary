package backend.diary.domain.article.dto.response;

public record UpdateArticleResponse(
        long articleId,
        String title,
        String content,
        String filePath
) {
}

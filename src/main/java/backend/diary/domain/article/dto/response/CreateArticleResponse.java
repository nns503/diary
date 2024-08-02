package backend.diary.domain.article.dto.response;

public record CreateArticleResponse(
        Long articleId,
        String title,
        String content,
        String filePath
) {
}

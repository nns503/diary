package backend.diary.domain.Article.dto.response;

public record CreateArticleResponse(
        Long articleId,
        String title,
        String content,
        String filePath
) {
}

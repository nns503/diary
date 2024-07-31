package backend.diary.domain.community.dto.response;

public record CreateArticleResponse(
        Long articleId,
        String title,
        String content,
        String filePath
) {
}

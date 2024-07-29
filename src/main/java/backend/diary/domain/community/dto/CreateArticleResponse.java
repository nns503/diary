package backend.diary.domain.community.dto;

public record CreateArticleResponse(
        Long articleId,
        String title,
        String content,
        String filePath
) {
}

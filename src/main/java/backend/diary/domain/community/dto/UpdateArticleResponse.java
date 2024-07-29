package backend.diary.domain.community.dto;

public record UpdateArticleResponse(
        long articleId,
        String title,
        String content,
        String filePath
) {
}

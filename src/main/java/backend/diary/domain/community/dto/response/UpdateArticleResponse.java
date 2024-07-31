package backend.diary.domain.community.dto.response;

public record UpdateArticleResponse(
        long articleId,
        String title,
        String content,
        String filePath
) {
}

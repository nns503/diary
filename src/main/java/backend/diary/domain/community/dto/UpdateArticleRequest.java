package backend.diary.domain.community.dto;

public record UpdateArticleRequest(
        String title,
        String content,
        String filePath
) {
}

package backend.diary.domain.community.dto;

public record CreateArticleRequest(
        String title,
        String content,
        String filePath
) { }

package backend.diary.domain.community.dto.request;

import jakarta.validation.constraints.Size;

public record CreateArticleRequest(
        @Size(min = 1, max = 50)
        String title,
        @Size(min = 1)
        String content,
        String filePath
) { }

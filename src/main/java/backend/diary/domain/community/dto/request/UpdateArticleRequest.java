package backend.diary.domain.community.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateArticleRequest(
        @NotEmpty(message = "제목을 작성해주세요")
        @Size(min = 1, max = 50, message = "제목은 1자에서 50자 사이로 작성해주세요")
        String title,
        @NotEmpty(message = "내용을 작성해주세요")
        @Size(min = 1, message = "내용을 작성해주세요")
        String content,
        String filePath
) {
}

package backend.diary.domain.comment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(
        @NotEmpty(message = "내용을 작성해주세요")
        @Size(min = 1, message = "내용을 작성해주세요")
        String content
) {
}

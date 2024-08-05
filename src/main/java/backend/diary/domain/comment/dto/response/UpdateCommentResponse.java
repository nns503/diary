package backend.diary.domain.comment.dto.response;

public record UpdateCommentResponse(
        Long commentId,
        String content
) {
}

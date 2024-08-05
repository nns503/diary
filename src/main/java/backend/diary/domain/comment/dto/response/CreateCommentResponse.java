package backend.diary.domain.comment.dto.response;

public record CreateCommentResponse(
        Long commentId,
        String content
) {
}

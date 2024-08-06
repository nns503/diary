package backend.diary.domain.comment.dto.response;

import backend.diary.domain.comment.entity.Comment;

public record UpdateCommentResponse(
        Long commentId,
        String content
) {
    public static UpdateCommentResponse of(Comment comment) {
        return new UpdateCommentResponse(comment.getId(), comment.getContent());
    }
}

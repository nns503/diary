package backend.diary.domain.comment.dto.response;

import backend.diary.domain.comment.entity.Comment;

public record CreateCommentResponse(
        Long commentId,
        String content
) {
    public static CreateCommentResponse of(Comment comment) {
        return new CreateCommentResponse(comment.getId(), comment.getContent());
    }
}

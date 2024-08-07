package backend.diary.domain.comment.dto.response;

import backend.diary.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record GetCommentResponse(
        long id,
        String author,
        String content,
        LocalDateTime createAt
) {
    public static GetCommentResponse convertTo(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}

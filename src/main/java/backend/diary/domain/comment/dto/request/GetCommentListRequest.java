package backend.diary.domain.comment.dto.request;

public record GetCommentListRequest(
        int page,
        int size
) {
}

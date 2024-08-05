package backend.diary.domain.comment.dto.response;

import java.util.List;

public record GetCommentListResponse(
        List<GetCommentResponse> comments,
        int page,
        int pageSize,
        int elements,
        Long totalElements,
        int totalPages
) {
}

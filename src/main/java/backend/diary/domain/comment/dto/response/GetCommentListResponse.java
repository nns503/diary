package backend.diary.domain.comment.dto.response;

import backend.diary.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetCommentListResponse(
        List<GetCommentResponse> comments,
        int page,
        int pageSize,
        int elements,
        Long totalElements,
        int totalPages
) {
    public static GetCommentListResponse of(Page<Comment> comments){
        List<GetCommentResponse> getCommentList = comments.getContent().stream()
                .map(GetCommentResponse::convertTo)
                .toList();

        int page = comments.getNumber() + 1;
        int pageSize = comments.getSize();
        int elements = comments.getNumberOfElements();
        long totalElements = comments.getTotalElements();
        int totalPages = comments.getTotalPages();

        return new GetCommentListResponse(getCommentList, page, pageSize, elements, totalElements, totalPages);
    }
}

package backend.diary.domain.comment.dto.response;

import backend.diary.domain.comment.entity.Comment;
import backend.diary.global.common.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetCommentListResponse(
        List<GetCommentResponse> comments,
        PageInfo pageinfo
) {
    public static GetCommentListResponse of(Page<Comment> comments){
        List<GetCommentResponse> getCommentList = comments.getContent().stream()
                .map(GetCommentResponse::convertTo)
                .toList();

        PageInfo pageInfo = PageInfo.of(comments);

        return new GetCommentListResponse(getCommentList, pageInfo);
    }
}

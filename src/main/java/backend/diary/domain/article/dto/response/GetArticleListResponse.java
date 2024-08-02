package backend.diary.domain.article.dto.response;

import java.util.List;

public record GetArticleListResponse(
        List<GetArticleResponse> articles,
        int page,
        int pageSize,
        int elements,
        Long totalElements,
        int totalPages
) { }

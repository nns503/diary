package backend.diary.domain.Article.dto.response;

import java.util.List;

public record GetArticleListResponse(
        List<GetArticleResponse> articles,
        int page,
        int size,
        Long totalElements,
        int totalPages
) { }

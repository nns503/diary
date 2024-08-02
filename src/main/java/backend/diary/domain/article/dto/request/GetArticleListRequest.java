package backend.diary.domain.article.dto.request;

public record GetArticleListRequest(
        int page,
        int size
)
{ }

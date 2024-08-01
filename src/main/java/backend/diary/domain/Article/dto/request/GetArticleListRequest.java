package backend.diary.domain.Article.dto.request;

public record GetArticleListRequest(
        int page,
        int size
)
{ }

package backend.diary.domain.like.dto.response;

import backend.diary.domain.article.entity.Article;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetLikeArticleListResponse(
        List<GetLikeArticleDTO> articles,
        Boolean hasNext
) {
    public static GetLikeArticleListResponse of(Slice<Article> articles){
        List<GetLikeArticleDTO> likeArticleResponseList = articles.getContent().stream()
                .map(GetLikeArticleDTO::of)
                .toList();

        boolean hasNext = articles.hasNext();
        return new GetLikeArticleListResponse(likeArticleResponseList, hasNext);
    }
}

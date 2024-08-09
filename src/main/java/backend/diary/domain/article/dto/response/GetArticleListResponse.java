package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;
import backend.diary.global.common.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetArticleListResponse(
        List<GetArticleDTO> articles,
        PageInfo pageInfo
) {
    public static GetArticleListResponse of(Page<Article> articles){
        List<GetArticleDTO> articleResponseList = articles.getContent().stream()
                .map(GetArticleDTO::toConvert)
                .toList();

        PageInfo pageInfo = PageInfo.of(articles);

        return new GetArticleListResponse(articleResponseList, pageInfo);
    }
}

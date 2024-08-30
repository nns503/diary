package backend.diary.domain.article.dto.response;

import backend.diary.global.common.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetArticleListResponse(
        List<GetArticleDTO> articles,
        PageInfo pageInfo
) {
    public static GetArticleListResponse of(Page<GetArticleDTO> articles){
        List<GetArticleDTO> articleResponseList = articles.getContent();
        PageInfo pageInfo = PageInfo.of(articles);

        return new GetArticleListResponse(articleResponseList, pageInfo);
    }
}

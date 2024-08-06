package backend.diary.domain.article.dto.response;

import backend.diary.domain.article.entity.Article;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetArticleListResponse(
        List<GetArticleDTO> articles,
        int page,
        int pageSize,
        int elements,
        Long totalElements,
        int totalPages
) {
    public static GetArticleListResponse of(Page<Article> articles){
        List<GetArticleDTO> articleResponseList = articles.getContent().stream()
                .map(GetArticleDTO::toConvert)
                .toList();

        int page = articles.getNumber() + 1;
        int pageSize = articles.getSize();
        int elements = articles.getNumberOfElements();
        long totalElements = articles.getTotalElements();
        int totalPages = articles.getTotalPages();

        return new GetArticleListResponse(articleResponseList, page, pageSize, elements, totalElements, totalPages);
    }
}

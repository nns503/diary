package backend.diary.domain.article.service;

import backend.diary.domain.article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.article.dto.response.GetArticleListResponse;
import backend.diary.domain.article.dto.response.GetArticleResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.NotFoundArticleException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetArticleService {

    private final ArticleRepository articleRepository;

    public GetArticleListResponse getArticleList(Pageable pageable){
        Page<Article> articles = articleRepository.findAllByIsDeletedFalse(pageable);
        List<GetArticleResponse> articleResponseList = articles.getContent().stream()
                .map(GetArticleResponse::convertTo)
                .toList();

        int page = articles.getNumber() + 1;
        int pageSize = articles.getSize();
        int elements = articles.getNumberOfElements();
        long totalElements = articles.getTotalElements();
        int totalPages = articles.getTotalPages();

        return new GetArticleListResponse(articleResponseList, page, pageSize, elements, totalElements, totalPages);
    }

    public GetArticleDetailResponse getArticle(Long articleId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        return GetArticleDetailResponse.convertTo(findArticle);
    }
}

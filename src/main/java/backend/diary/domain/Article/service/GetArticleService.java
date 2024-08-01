package backend.diary.domain.Article.service;

import backend.diary.domain.Article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.Article.dto.response.GetArticleListResponse;
import backend.diary.domain.Article.dto.response.GetArticleResponse;
import backend.diary.domain.Article.entity.Article;
import backend.diary.domain.Article.entity.repository.ArticleRepository;
import backend.diary.domain.Article.exception.NotFoundArticleException;
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
        int size = articles.getSize();
        long totalElements = articles.getTotalElements();
        int totalPages = articles.getTotalPages();

        return new GetArticleListResponse(articleResponseList, page, size, totalElements, totalPages);
    }

    public GetArticleDetailResponse getArticle(Long articleId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        return GetArticleDetailResponse.convertTo(findArticle);
    }
}

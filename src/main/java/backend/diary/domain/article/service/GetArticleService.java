package backend.diary.domain.article.service;

import backend.diary.domain.article.dto.response.GetArticleDTO;
import backend.diary.domain.article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.article.dto.response.GetArticleListResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.NotFoundArticleException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetArticleService {

    private final ArticleRepository articleRepository;

    public GetArticleListResponse getArticleList(Pageable pageable, String keywordType, String keyword, String sort){
        Page<GetArticleDTO> articles = articleRepository.findByArticleList(pageable, keywordType, keyword, sort);

        return GetArticleListResponse.of(articles);
    }

    @Transactional
    public GetArticleDetailResponse getArticle(Long articleId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        articleRepository.increaseViewCount(articleId);
        return GetArticleDetailResponse.of(findArticle);
    }
}

package backend.diary.domain.community.service;

import backend.diary.domain.community.dto.response.GetArticleResponse;
import backend.diary.domain.community.dto.response.GetArticlesResponse;
import backend.diary.domain.community.entity.Article;
import backend.diary.domain.community.entity.repository.ArticleRepository;
import backend.diary.domain.community.exception.NotFoundArticleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetArticleService {

    private final ArticleRepository articleRepository;

    public List<GetArticlesResponse> getArticles() {
        return articleRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc().stream()
                .map(GetArticlesResponse::convertTo)
                .toList();
    }

    public GetArticleResponse getArticle(Long articleId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        return GetArticleResponse.convertTo(findArticle);
    }
}

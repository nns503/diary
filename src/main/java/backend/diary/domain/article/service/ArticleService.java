package backend.diary.domain.article.service;

import backend.diary.domain.article.dto.request.CreateArticleRequest;
import backend.diary.domain.article.dto.request.UpdateArticleRequest;
import backend.diary.domain.article.dto.response.CreateArticleResponse;
import backend.diary.domain.article.dto.response.UpdateArticleResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.AlreadyDeletedArticleException;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.article.exception.UnauthorizedArticleException;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public CreateArticleResponse createArticle(User user, CreateArticleRequest request) {

        Article article = Article.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .isDeleted(false)
                .build();

        Article craeteArticle = articleRepository.save(article);

        return CreateArticleResponse.of(craeteArticle);
    }

    @Transactional
    public void deleteArticle(User user, Long articleId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        validateDeleteArticle(findArticle);
        validateArticleAuthor(user, findArticle);

        findArticle.delete();
        articleRepository.save(findArticle);
    }

    @Transactional
    public UpdateArticleResponse updateArticle(User user, Long articleId, UpdateArticleRequest request){
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);

        validateDeleteArticle(findArticle);
        validateArticleAuthor(user, findArticle);

        findArticle.update(request.title(), request.content());
        Article updateArticle = articleRepository.save(findArticle);

        return UpdateArticleResponse.of(updateArticle);
    }

    private void validateArticleAuthor(User user, Article article) {
        Long userId = user.getId();
        Long articleUserId = article.getUser().getId();
        if (articleUserId == null || !articleUserId.equals(userId)) {
            throw new UnauthorizedArticleException();
        }
    }

    private void validateDeleteArticle(Article findArticle) {
        if(findArticle.getIsDeleted()){
            throw new AlreadyDeletedArticleException();
        }
    }
}

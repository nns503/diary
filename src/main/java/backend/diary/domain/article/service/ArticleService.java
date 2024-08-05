package backend.diary.domain.article.service;

import backend.diary.domain.article.dto.request.CreateArticleRequest;
import backend.diary.domain.article.dto.response.CreateArticleResponse;
import backend.diary.domain.article.dto.request.UpdateArticleRequest;
import backend.diary.domain.article.dto.response.UpdateArticleResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.ArticleAlreadyDeletedException;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.article.exception.UnauthorizedArticleException;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateArticleResponse createArticle(CreateArticleRequest request, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(NotFoundUserException::new);

        Article article = Article.builder()
                .author(user.getNickname())
                .title(request.title())
                .content(request.content())
                .filePath(request.filePath())
                .user(user)
                .isDeleted(false)
                .build();

        Article savedArticle = articleRepository.save(article);

        return new CreateArticleResponse(savedArticle.getId(), savedArticle.getTitle(), savedArticle.getContent(), savedArticle.getFilePath());
    }

    @Transactional
    public void deleteArticle(Long articleId, Long userId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        Long findUserId = findArticle.getUser().getId();

        validateDeleteArticle(findArticle);
        validateArticleAuthor(userId, findUserId);

        findArticle.delete();
        articleRepository.save(findArticle);
    }

    @Transactional
    public UpdateArticleResponse updateArticle(Long articleId, UpdateArticleRequest request, Long userId){
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        Long findUserId = findArticle.getUser().getId();

        validateDeleteArticle(findArticle);
        validateArticleAuthor(userId, findUserId);

        findArticle.update(request.title(), request.content(), request.filePath());
        Article updatedArticle = articleRepository.save(findArticle);

        return new UpdateArticleResponse(updatedArticle.getId(), updatedArticle.getTitle(), updatedArticle.getContent(), updatedArticle.getFilePath());
    }

    private void validateArticleAuthor(Long userId, Long findUserId) {
        if (findUserId == null || !findUserId.equals(userId)) {
            throw new UnauthorizedArticleException();
        }
    }

    private void validateDeleteArticle(Article findArticle) {
        if(findArticle.getIsDeleted()){
            throw new ArticleAlreadyDeletedException();
        }
    }
}

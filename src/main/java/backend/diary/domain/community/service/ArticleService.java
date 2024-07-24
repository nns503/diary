package backend.diary.domain.community.service;

import backend.diary.domain.community.dto.*;
import backend.diary.domain.community.entity.Article;
import backend.diary.domain.community.entity.repository.ArticleRepository;
import backend.diary.domain.community.exception.NotFoundArticleException;
import backend.diary.domain.community.exception.UnauthorizedArticleException;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CreateArticleResponse createArticle(CreateArticleRequest request, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(NotFoundUserException::new);

        Article article = Article.builder()
                .title(request.title())
                .content(request.content())
                .filePath(request.filePath())
                .user(user)
                .isDeleted(false)
                .build();

        Article savedArticle = articleRepository.save(article);

        return new CreateArticleResponse(savedArticle.getId());
    }

    public void deleteArticle(Long articleId, Long userId) {
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        Long findUserId = findArticle.getUser().getId();

        validateArticleAuthor(userId, findUserId);
        findArticle.delete();

        articleRepository.save(findArticle);
    }

    public UpdateArticleResponse updateArticle(Long articleId, UpdateArticleRequest request, Long userId){
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        Long findUserId = findArticle.getUser().getId();

        validateArticleAuthor(userId, findUserId);
        findArticle.update(request.title(), request.content(), request.filePath());
        Article updatedArticle = articleRepository.save(findArticle);

        return new UpdateArticleResponse(updatedArticle.getId());
    }

    private void validateArticleAuthor(Long userId, Long findUserId) {
        if (!findUserId.equals(userId)) {
            throw new UnauthorizedArticleException();
        }
    }
}

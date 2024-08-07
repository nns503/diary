package backend.diary.domain.like.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.like.entity.Like;
import backend.diary.domain.like.entity.repository.LikeRepository;
import backend.diary.domain.like.exception.AlreadyLikedException;
import backend.diary.domain.like.exception.NotFoundLikedException;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void createLike(User user, Long articleId){
        if(likeRepository.existsByUserIdAndArticleId(user.getId(), articleId)){
            throw new AlreadyLikedException();
        }

        Article article = articleRepository.findByIdAndIsDeletedFalse(articleId)
                .orElseThrow(NotFoundArticleException::new);

        Like like = Like.builder()
                .user(user)
                .article(article)
                .build();

        article.likeCountPlus();
        likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(User user, Long articleId){
        Like like = likeRepository.findByUserIdAndArticleId(user.getId(), articleId)
                .orElseThrow(NotFoundLikedException::new);

        Article article = articleRepository.findByIdAndIsDeletedFalse(articleId)
                .orElseThrow(NotFoundArticleException::new);

        article.likeCountMinus();
        likeRepository.delete(like);
    }
}

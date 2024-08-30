package backend.diary.domain.like.entity.repository;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.like.entity.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
    Optional<Like> findByUserIdAndArticleId(Long userId, Long articleId);

    @Query(value = "select a from Article a join Like l  on a.id = l.article.id where l.user.id = :userId")
    Slice<Article> findLikeArticle(Pageable pageable, Long userId);
}

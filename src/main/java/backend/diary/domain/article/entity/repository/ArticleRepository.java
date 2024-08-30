package backend.diary.domain.article.entity.repository;

import backend.diary.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository
        extends JpaRepository<Article, Long>, GetArticleRepository {

    Boolean existsByIdAndIsDeletedFalse(Long id);

    Optional<Article> findByIdAndIsDeletedFalse(Long articleId);

    @Modifying
    @Query("UPDATE Article a SET a.viewCount = a.viewCount + 1 WHERE a.id = :articleId")
    void increaseViewCount(Long articleId);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount + 1 WHERE a.id = :articleId")
    void increaseLikeCount(Long articleId);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount - 1 WHERE a.id = :articleId")
    void decreaseLikeCount(Long articleId);
}

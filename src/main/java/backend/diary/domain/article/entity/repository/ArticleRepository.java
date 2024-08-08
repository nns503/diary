package backend.diary.domain.article.entity.repository;

import backend.diary.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Boolean existsByIdAndIsDeletedFalse(Long id);

    Page<Article> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Article> findByIdAndIsDeletedFalse(Long id);

    @Modifying
    @Query("UPDATE Article a SET a.viewCount = a.viewCount + 1 WHERE a.id = :id")
    void increaseViewCount(Long id);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount + 1 WHERE a.id = :id")
    void increaseLikeCount(Long id);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount - 1 WHERE a.id = :id")
    void decreaseLikeCount(Long id);
}

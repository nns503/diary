package backend.diary.domain.article.entity.repository;

import backend.diary.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Article> findByIdAndIsDeletedFalse(Long id);
}

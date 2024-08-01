package backend.diary.domain.Article.entity.repository;

import backend.diary.domain.Article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByIsDeletedFalse(Pageable pageable);
}

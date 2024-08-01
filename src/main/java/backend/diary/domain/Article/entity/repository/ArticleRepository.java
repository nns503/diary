package backend.diary.domain.Article.entity.repository;

import backend.diary.domain.Article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByIsDeletedFalseOrderByCreatedAtDesc();
}

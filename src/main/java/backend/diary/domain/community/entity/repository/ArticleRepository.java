package backend.diary.domain.community.entity.repository;

import backend.diary.domain.community.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByIsDeletedFalseOrderByCreatedAtDesc();
}

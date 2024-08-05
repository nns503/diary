package backend.diary.domain.comment.entity.repository;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByArticleAndIsDeletedFalse(Article article, Pageable pageable);
}

package backend.diary.domain.comment.entity.repository;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join c.article a where a = :article and c.isDeleted = false order by c.createdAt desc",
            countQuery = "select count(c) from Comment c join c.article a where a = :article and c.isDeleted = false")
    Page<Comment> findAllByArticleAndIsDeletedFalse(Article article, Pageable pageable);
}

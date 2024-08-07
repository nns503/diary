package backend.diary.domain.comment.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.AlreadyDeletedArticleException;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.comment.dto.request.CreateCommentRequest;
import backend.diary.domain.comment.dto.request.UpdateCommentRequest;
import backend.diary.domain.comment.dto.response.CreateCommentResponse;
import backend.diary.domain.comment.dto.response.UpdateCommentResponse;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.comment.entity.repository.CommentRepository;
import backend.diary.domain.comment.exception.AlreadyDeletedCommentException;
import backend.diary.domain.comment.exception.NotFoundCommentException;
import backend.diary.domain.comment.exception.UnauthorizedCommentException;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public CreateCommentResponse createComment(User user, CreateCommentRequest request, Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        validateDeleteComment(article);

        Comment comment = Comment.builder()
                .content(request.content())
                .article(article)
                .user(user)
                .isDeleted(false)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CreateCommentResponse.of(savedComment);
    }

    @Transactional
    public void deleteComment(User user, Long commentId){
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);

        validateDeleteComment(findComment);
        validateCommentAuthor(user, findComment);

        findComment.delete();
        commentRepository.save(findComment);
    }

    @Transactional
    public UpdateCommentResponse updateComment(User user, UpdateCommentRequest request, Long commentId){
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);

        validateDeleteComment(findComment);
        validateCommentAuthor(user, findComment);

        findComment.update(request.content());
        Comment updatedComment = commentRepository.save(findComment);

        return UpdateCommentResponse.of(updatedComment);
    }

    private void validateDeleteComment(Article findArticle) {
        if(findArticle.getIsDeleted()){
            throw new AlreadyDeletedArticleException();
        }
    }

    private void validateCommentAuthor(User user, Comment comment) {
        Long userId = user.getId();
        Long commentUserId = comment.getUser().getId();
        if (commentUserId == null || !commentUserId.equals(userId)) {
            throw new UnauthorizedCommentException();
        }
    }

    private void validateDeleteComment(Comment findComment) {
        if(findComment.getIsDeleted()){
            throw new AlreadyDeletedCommentException();
        }
    }
}

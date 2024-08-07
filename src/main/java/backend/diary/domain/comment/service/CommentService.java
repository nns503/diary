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
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request, Long articleId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        validateDeleteComment(article);

        Comment comment = Comment.builder()
                .author(user.getNickname())
                .content(request.content())
                .article(article)
                .user(user)
                .isDeleted(false)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CreateCommentResponse.of(savedComment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId){
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        Long findUserId = findComment.getUser().getId();

        validateDeleteComment(findComment);
        validateCommentAuthor(findUserId, userId);

        findComment.delete();
        commentRepository.save(findComment);
    }

    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest request, Long commentId, Long userId){
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        Long findUserId = findComment.getUser().getId();

        validateDeleteComment(findComment);
        validateCommentAuthor(findUserId, userId);

        findComment.update(request.content());
        Comment updatedComment = commentRepository.save(findComment);

        return UpdateCommentResponse.of(updatedComment);
    }

    private void validateDeleteComment(Article findArticle) {
        if(findArticle.getIsDeleted()){
            throw new AlreadyDeletedArticleException();
        }
    }

    private void validateCommentAuthor(Long userId, Long findUserId) {
        if (findUserId == null || !findUserId.equals(userId)) {
            throw new UnauthorizedCommentException();
        }
    }

    private void validateDeleteComment(Comment findComment) {
        if(findComment.getIsDeleted()){
            throw new AlreadyDeletedCommentException();
        }
    }
}

package backend.diary.domain.comment.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.AlreadyDeletedArticleException;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.comment.dto.request.CreateCommentRequest;
import backend.diary.domain.comment.dto.request.UpdateCommentRequest;
import backend.diary.domain.comment.dto.response.CreateCommentResponse;
import backend.diary.domain.comment.dto.response.UpdateCommentResponse;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.comment.entity.repository.CommentRepository;
import backend.diary.domain.comment.exception.AlreadyDeletedCommentException;
import backend.diary.domain.comment.exception.NotFoundCommentException;
import backend.diary.domain.comment.exception.UnauthorizedCommentException;
import backend.diary.domain.comment.fixture.CommentFixture;
import backend.diary.domain.user.entity.User;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ArticleRepository articleRepository;

    private final CommonUserFixture userFixture = new CommonUserFixture();
    private final ArticleFixture articleFixture = new ArticleFixture();
    private final CommentFixture commentFixture = new CommentFixture();

    private User user_일반회원1;
    private User user_일반회원2;
    private Article article1;
    private Comment comment1;


    @BeforeEach
    void setup(){
        user_일반회원1 = userFixture.일반회원1;
        user_일반회원2 = userFixture.일반회원2;
        article1 = articleFixture.기본게시물1(user_일반회원1);
        comment1 = commentFixture.기본댓글1(user_일반회원1, article1);
    }

    @Test
    void 댓글_등록한다_성공(){
        CreateCommentRequest request = new CreateCommentRequest(comment1.getContent());
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));
        given(commentRepository.save(any(Comment.class)))
                .willReturn(comment1);

        CreateCommentResponse response = commentService
                .createComment(user_일반회원1, request, article1.getId());

        assertThat(response.commentId()).isEqualTo(comment1.getId());
        assertThat(response.content()).isEqualTo(comment1.getContent());
    }

    @Test
    void 댓글_등록한다_실패_존재하지않는_게시글(){
        CreateCommentRequest request = new CreateCommentRequest(comment1.getContent());
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class,
                () ->
                        commentService
                                .createComment(user_일반회원1, request, article1.getId()));
    }

    @Test
    void 댓글_등록한다_실패_삭제된_게시글(){
        CreateCommentRequest request = new CreateCommentRequest(comment1.getContent());
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));
        article1.delete();

        assertThrows(AlreadyDeletedArticleException.class,
                () ->
                        commentService
                                .createComment(user_일반회원1, request, article1.getId()));
    }

    @Test
    void 댓글_삭제한다_성공(){
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));

        commentService.deleteComment(user_일반회원1, comment1.getId());

        assertThat(comment1.getIsDeleted()).isEqualTo(true);
    }

    @Test
    void 댓글_삭제한다_실패_존재하지_않는_댓글(){
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundCommentException.class,
                () ->
                        commentService
                                .deleteComment(user_일반회원1, comment1.getId()));
    }

    @Test
    void 댓글_삭제한다_이미_삭제된_댓글(){
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));

        comment1.delete();

        assertThrows(AlreadyDeletedCommentException.class,
                () ->
                        commentService
                                .deleteComment(user_일반회원1, comment1.getId()));
    }

    @Test
    void 댓글_삭제한다_실패_작성자가_아님(){
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));

        assertThrows(UnauthorizedCommentException.class,
                () ->
                        commentService
                                .deleteComment(user_일반회원2, comment1.getId()));
    }

    @Test
    void 댓글_수정한다_성공(){
        UpdateCommentRequest request = new UpdateCommentRequest("수정된 내용");
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));
        given(commentRepository.save(any(Comment.class)))
                .willReturn(comment1);

        UpdateCommentResponse response = commentService
                .updateComment(user_일반회원1, request, comment1.getId());

        assertThat(response.commentId()).isEqualTo(comment1.getId());
        assertThat(response.content()).isEqualTo(request.content());
    }

    @Test
    void 댓글_수정한다_실패_존재하지_않는_댓글(){
        UpdateCommentRequest request = new UpdateCommentRequest("수정된 내용");
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundCommentException.class,
                () ->
                        commentService
                                .updateComment(user_일반회원1, request, comment1.getId()));
    }

    @Test
    void 댓글_수정한다_이미_삭제된_댓글(){
        UpdateCommentRequest request = new UpdateCommentRequest("수정된 내용");
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));
        comment1.delete();

        assertThrows(AlreadyDeletedCommentException.class,
                () ->
                        commentService
                                .updateComment(user_일반회원1, request, comment1.getId()));
    }

    @Test
    void 댓글_수정한다_실패_작성자가_아님(){
        UpdateCommentRequest request = new UpdateCommentRequest("수정된 내용");
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(comment1));

        assertThrows(UnauthorizedCommentException.class,
                () ->
                        commentService
                                .updateComment(user_일반회원2, request, comment1.getId()));
    }
}
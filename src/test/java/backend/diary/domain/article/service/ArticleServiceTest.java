package backend.diary.domain.article.service;

import backend.diary.domain.article.dto.request.CreateArticleRequest;
import backend.diary.domain.article.dto.response.CreateArticleResponse;
import backend.diary.domain.article.dto.request.UpdateArticleRequest;
import backend.diary.domain.article.dto.response.UpdateArticleResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.ArticleAlreadyDeletedException;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.article.exception.UnauthorizedArticleException;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserRepository userRepository;

    private final ArticleFixture articleFixture = new ArticleFixture();
    private final CommonUserFixture commonUserFixture = new CommonUserFixture();

    private Article article1;
    private User user_일반회원1;
    private User user_일반회원2;

    @BeforeEach
    void setup(){
        user_일반회원1 = commonUserFixture.일반회원1;
        user_일반회원2 = commonUserFixture.일반회원2;
        article1 = articleFixture.기본게시물1(user_일반회원1);
    }

    @Test
    void 게시글을_등록한다_성공(){
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(article1.getTitle(), article1.getContent(), article1.getFilePath());
        given(userRepository.findById(user_일반회원1.getId())).willReturn(Optional.ofNullable(user_일반회원1));
        given(articleRepository.save(any(Article.class))).willReturn(article1);

        CreateArticleResponse response = articleService.createArticle(createArticleRequest, user_일반회원1.getId());

        assertThat(response.articleId()).isEqualTo(article1.getId());
        assertThat(response.title()).isEqualTo(article1.getTitle());
        assertThat(response.content()).isEqualTo(article1.getContent());
        assertThat(response.filePath()).isEqualTo(article1.getFilePath());
    }

    @Test
    void 게시글을_삭제한다_성공(){
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));

        articleService.deleteArticle(article1.getId(), user_일반회원1.getId());

        assertThat(article1.getIsDeleted()).isEqualTo(true);
    }

    @Test
    void 게시글을_삭제한다_실패_게시글이_존재하지_않음(){
        given(articleRepository.findById(article1.getId())).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class,
                ()->
                        articleService.deleteArticle(article1.getId(), user_일반회원1.getId()));
    }

    @Test
    void 게시글을_삭제한다_실패_이미_삭제된_게시글(){
        article1.delete();
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));

        assertThrows(ArticleAlreadyDeletedException.class,
                ()->
                        articleService.deleteArticle(article1.getId(), user_일반회원1.getId()));
    }

    @Test
    void 게시글을_삭제한다_실패_작성자와_다른_작성자가_삭제(){
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));

        assertThrows(UnauthorizedArticleException.class,
                ()->
                        articleService.deleteArticle(article1.getId(), user_일반회원2.getId()));
    }

    @Test
    void 게시글을_수정한다_성공(){
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("수정된 제목", "수정된 내용", "수정된 주소");
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));
        given(articleRepository.save(any(Article.class))).willReturn(article1);

        UpdateArticleResponse response = articleService.updateArticle(article1.getId(), updateArticleRequest, user_일반회원1.getId());

        assertThat(response.articleId()).isEqualTo(article1.getId());
        assertThat(response.title()).isEqualTo(updateArticleRequest.title());
        assertThat(response.content()).isEqualTo(updateArticleRequest.content());
        assertThat(response.filePath()).isEqualTo(updateArticleRequest.filePath());
    }

    @Test
    void 게시글을_수정한다_실패_게시글이_존재하지_않음(){
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("수정된 제목", "수정된 내용", "수정된 주소");
        given(articleRepository.findById(article1.getId())).willReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class,
                ()->
                        articleService.updateArticle(article1.getId(), updateArticleRequest, user_일반회원1.getId()));
    }

    @Test
    void 게시글을_수정한다_실패_이미_삭제된_게시글(){
        article1.delete();
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("수정된 제목", "수정된 내용", "수정된 주소");
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));

        assertThrows(ArticleAlreadyDeletedException.class,
                ()->
                        articleService.updateArticle(article1.getId(), updateArticleRequest, user_일반회원1.getId()));
    }

    @Test
    void 게시글을_수정한다_실패_작성자와_다른_작성자가_수정(){
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("수정된 제목", "수정된 내용", "수정된 주소");
        given(articleRepository.findById(article1.getId())).willReturn(Optional.ofNullable(article1));

        assertThrows(UnauthorizedArticleException.class,
                ()->
                        articleService.updateArticle(article1.getId(), updateArticleRequest, user_일반회원2.getId()));
    }

}
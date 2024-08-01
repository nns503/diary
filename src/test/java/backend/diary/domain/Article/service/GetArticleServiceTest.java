package backend.diary.domain.Article.service;

import backend.diary.domain.Article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.Article.entity.Article;
import backend.diary.domain.Article.entity.repository.ArticleRepository;
import backend.diary.domain.Article.exception.NotFoundArticleException;
import backend.diary.domain.Article.fixture.ArticleFixture;
import backend.diary.domain.user.entity.User;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class GetArticleServiceTest {

    @InjectMocks
    private GetArticleService getArticleService;

    @Mock
    private ArticleRepository articleRepository;

    private final ArticleFixture articleFixture = new ArticleFixture();
    private final CommonUserFixture commonUserFixture = new CommonUserFixture();

    private Article article1;

    @BeforeEach
    void setUp(){
        User 일반회원1 = commonUserFixture.일반회원1;
        article1 = articleFixture.기본게시물1(일반회원1);
    }

    @ParameterizedTest()
    void 게시글_목록_조회_성공(){

    }

    @Test
    void 단일_게시글_조회_성공(){
        Long articleId = article1.getId();
        given(articleRepository.findById(articleId))
                .willReturn(Optional.ofNullable(article1));

        GetArticleDetailResponse getArticleResponse = getArticleService.getArticle(articleId);

        assertThat(getArticleResponse.title()).isEqualTo(article1.getTitle());
        assertThat(getArticleResponse.content()).isEqualTo(article1.getContent());
        assertThat(getArticleResponse.author()).isEqualTo(article1.getAuthor());
        assertThat(getArticleResponse.createAt()).isEqualTo(article1.getCreatedAt());
        assertThat(getArticleResponse.filePath()).isEqualTo(article1.getFilePath());
    }

    @Test
    void 단일_게시글_조회_실패_존재하지_않는_게시글(){
        Long articleId = article1.getId();
        given(articleRepository.findById(articleId))
                .willReturn(Optional.empty());
        assertThrows(NotFoundArticleException.class,
                () ->
                        getArticleService.getArticle(articleId));

    }
    
}
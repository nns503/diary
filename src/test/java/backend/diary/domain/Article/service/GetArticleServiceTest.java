package backend.diary.domain.Article.service;

import backend.diary.domain.Article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.Article.dto.response.GetArticleListResponse;
import backend.diary.domain.Article.dto.response.GetArticleResponse;
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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

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
    @MethodSource("providePageableParameters")
    void 게시글_목록_조회_성공(Pageable pageable){
        List<Article> articles = new ArrayList<>();
        for(long i=1; i<=33; i++){
            Article article = Article.builder()
                    .id(i)
                    .build();
            articles.add(article);
        }
        int totalElements = articles.size();
        int start = Math.min((int) pageable.getOffset(), totalElements);
        int end = Math.min(start + pageable.getPageSize(), totalElements);

        List<Article> subList = articles.subList(start, end);
        Page<Article> pageArticles = new PageImpl<>(subList, pageable, totalElements);
        given(articleRepository.findAllByIsDeletedFalse(any(Pageable.class)))
                .willReturn(pageArticles);

        GetArticleListResponse articleList = getArticleService.getArticleList(pageable);
        int expectedSize = (totalElements > start) ? Math.min(pageable.getPageSize(), totalElements - start) : 0;

        long index = start + 1;
        for (GetArticleResponse article : articleList.articles()) {
            assertThat(article.id()).isEqualTo(index++);
        }
        assertThat(articleList.page()).isEqualTo(pageable.getPageNumber() + 1);
        assertThat(articleList.pageSize()).isEqualTo(pageable.getPageSize());
        assertThat(articleList.elements()).isEqualTo(expectedSize);
        assertThat(articleList.totalElements()).isEqualTo(pageArticles.getTotalElements());
        assertThat(articleList.totalPages()).isEqualTo(pageArticles.getTotalPages());
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

    static Stream<Arguments> providePageableParameters() {
        return Stream.of(
                Arguments.of(PageRequest.of(0, 5)),
                Arguments.of(PageRequest.of(0, 10)),
                Arguments.of(PageRequest.of(1, 5)),
                Arguments.of(PageRequest.of(3, 10)),
                Arguments.of(PageRequest.of(2, 100)) // 빈 페이지
        );
    }
}
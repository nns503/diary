package backend.diary.domain.article.controller;

import backend.diary.annotation.WithMockCustomUser;
import backend.diary.common.ControllerTest;
import backend.diary.domain.article.dto.request.GetArticleListRequest;
import backend.diary.domain.article.dto.response.GetArticleDetailResponse;
import backend.diary.domain.article.dto.response.GetArticleListResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.article.service.GetArticleService;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(value = GetArticleController.class)
class GetArticleControllerTest extends ControllerTest {

    @MockBean
    private GetArticleService getArticleService;

    private final CommonUserFixture commonUserFixture = new CommonUserFixture();
    private final ArticleFixture articleFixture = new ArticleFixture();

    @ParameterizedTest()
    @MethodSource("provideGetArticleListRequestParameters")
    @WithMockCustomUser
    void 게시글_목록을_조회한다_성공_USER(int page, int size) throws Exception {
        GetArticleListRequest getArticleListRequest = new GetArticleListRequest(page, size);
        String request = objectMapper.writeValueAsString(getArticleListRequest);
        PageRequest pageable = PageRequest.of(page, size);
        List<Article> articles = new ArrayList<>();
        for(long i=1; i<=33; i++){
            Article article = Article.builder()
                    .id(i)
                    .user(commonUserFixture.일반회원1)
                    .build();
            articles.add(article);
        }
        int totalElements = articles.size();
        int start = Math.min((int) pageable.getOffset(), totalElements);
        int end = Math.min(start + pageable.getPageSize(), totalElements);
        List<Article> subList = articles.subList(start, end);
        Page<Article> pageArticles = new PageImpl<>(subList, pageable, totalElements);
        GetArticleListResponse getArticleListResponse = GetArticleListResponse.of(pageArticles);
        given(getArticleService.getArticleList(any(Pageable.class)))
                .willReturn(getArticleListResponse);

        mvc.perform(get("/api/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void 단일_게시글을_조회한다_성공_USER() throws Exception {
        Long articleId = 1L;
        String request = objectMapper.writeValueAsString(articleId);
        GetArticleDetailResponse response = GetArticleDetailResponse.of(articleFixture.기본게시물1(commonUserFixture.일반회원1));
        given(getArticleService.getArticle(anyLong()))
                .willReturn(response);

        mvc.perform(get("/api/article/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    static Stream<Arguments> provideGetArticleListRequestParameters() {
        return Stream.of(
                Arguments.of(1, 5),
                Arguments.of(1, 10),
                Arguments.of(1, 32),
                Arguments.of(1, 33),
                Arguments.of(1, 34),
                Arguments.of(3, 10),
                Arguments.of(2, 100)
        );
    }

}
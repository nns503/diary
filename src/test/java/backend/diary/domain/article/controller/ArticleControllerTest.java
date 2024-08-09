package backend.diary.domain.article.controller;

import backend.diary.annotation.WithMockCustomUser;
import backend.diary.common.ControllerTest;
import backend.diary.domain.article.dto.request.CreateArticleRequest;
import backend.diary.domain.article.dto.request.DeleteArticleRequest;
import backend.diary.domain.article.dto.request.UpdateArticleRequest;
import backend.diary.domain.article.dto.response.CreateArticleResponse;
import backend.diary.domain.article.dto.response.UpdateArticleResponse;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.article.service.ArticleService;
import backend.diary.domain.user.entity.User;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(value = ArticleController.class)
class ArticleControllerTest extends ControllerTest {

    @MockBean
    private ArticleService articleService;

    ArticleFixture articleFixture = new ArticleFixture();
    CommonUserFixture commonUserFixture = new CommonUserFixture();

    private Article article1;

    @BeforeEach
    void setup(){
        User user_일반회원1 = commonUserFixture.일반회원1;
        article1 = articleFixture.기본게시물1(user_일반회원1);
    }

    @Test
    @WithMockCustomUser
    void 게시글을_작성한다_성공_USER() throws Exception {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(article1.getTitle(), article1.getContent());
        String request  = objectMapper.writeValueAsString(createArticleRequest);

        given(articleService.createArticle(any(User.class), any(CreateArticleRequest.class)))
                .willReturn(new CreateArticleResponse(article1.getId(), article1.getTitle(), article1.getContent()));

        mvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value(article1.getId()))
                .andExpect(jsonPath("$.title").value(article1.getTitle()))
                .andExpect(jsonPath("$.content").value(article1.getContent()));

    }

    @Test
    void 게시글을_작성한다_실패_인증받지_못한_회원() throws Exception {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(article1.getTitle(), article1.getContent());
        String request  = objectMapper.writeValueAsString(createArticleRequest);

        mvc.perform(post("/api/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_작성한다_실패_잘못된_제목_내용없음() throws Exception {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("", article1.getContent());
        String request  = objectMapper.writeValueAsString(createArticleRequest);

        mvc.perform(post("/api/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_작성한다_실패_잘못된_제목_NULL() throws Exception {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(null, article1.getContent());
        String request  = objectMapper.writeValueAsString(createArticleRequest);

        mvc.perform(post("/api/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockCustomUser
    void 게시글을_작성한다_실패_잘못된_제목_너무김() throws Exception {
        String LongTitle = "준비가 안됐다구요 소용돌이쳐 어지럽다구 쏟아지는 맘을 멈출 수가 없을까? 너의 작은 인사 한마디에 요란해져서 네 맘의 비밀번호 눌러 열고 싶지만 너를 고민고민해도 좋은 걸 어쩌니";
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(LongTitle, article1.getContent());
        String request  = objectMapper.writeValueAsString(createArticleRequest);

        mvc.perform(post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_삭제한다_성공_USER() throws Exception {
        DeleteArticleRequest deleteArticleRequest = new DeleteArticleRequest(article1.getId());
        String request  = objectMapper.writeValueAsString(deleteArticleRequest);

        willDoNothing().given(articleService).deleteArticle(any(User.class), anyLong());

        mvc.perform(delete("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("게시글을 삭제했습니다."));
    }

    @Test
    void 게시글을_삭제한다_실패_인증받지_못한_회원() throws Exception {
        DeleteArticleRequest deleteArticleRequest = new DeleteArticleRequest(article1.getId());
        String request  = objectMapper.writeValueAsString(deleteArticleRequest);

        mvc.perform(delete("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_수정한다_성공_USER() throws Exception {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(article1.getTitle(), article1.getContent());
        String request  = objectMapper.writeValueAsString(updateArticleRequest);

        given(articleService.updateArticle(any(User.class), anyLong(), any(UpdateArticleRequest.class)))
                .willReturn(new UpdateArticleResponse(article1.getId(), article1.getTitle(), article1.getContent()));

        mvc.perform(put("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value(article1.getId()))
                .andExpect(jsonPath("$.title").value(article1.getTitle()))
                .andExpect(jsonPath("$.content").value(article1.getContent()));
    }

    @Test
    void 게시글을_수정한다_실패_인증받지_못한_회원() throws Exception {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(article1.getTitle(), article1.getContent());
        String request  = objectMapper.writeValueAsString(updateArticleRequest);

        mvc.perform(put("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_수정한다_실패_잘못된_내용_내용없음() throws Exception {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(article1.getTitle(), "");
        String request  = objectMapper.writeValueAsString(updateArticleRequest);

        mvc.perform(put("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser
    void 게시글을_수정한다_실패_잘못된_내용_NULL() throws Exception {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(article1.getTitle(), null);
        String request  = objectMapper.writeValueAsString(updateArticleRequest);

        mvc.perform(put("/api/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
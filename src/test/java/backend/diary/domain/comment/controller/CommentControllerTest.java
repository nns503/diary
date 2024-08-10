package backend.diary.domain.comment.controller;

import backend.diary.annotation.WithMockCustomUser;
import backend.diary.common.ControllerTest;
import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.comment.dto.request.CreateCommentRequest;
import backend.diary.domain.comment.dto.request.UpdateCommentRequest;
import backend.diary.domain.comment.dto.response.CreateCommentResponse;
import backend.diary.domain.comment.dto.response.UpdateCommentResponse;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.comment.fixture.CommentFixture;
import backend.diary.domain.comment.service.CommentService;
import backend.diary.domain.user.entity.User;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CommentController.class)
class CommentControllerTest extends ControllerTest {

    @MockBean
    private CommentService commentService;

    CommonUserFixture commonUserFixture = new CommonUserFixture();
    ArticleFixture articleFixture = new ArticleFixture();
    CommentFixture commentFixture = new CommentFixture();

    private Comment comment1;

    @BeforeEach
    void setUp() {
        User user1 = commonUserFixture.일반회원1;
        Article article1 = articleFixture.기본게시물1(user1);
        comment1 = commentFixture.기본댓글1(user1, article1);
    }

    @Test
    @WithMockCustomUser
    void 댓글_작성한다_성공_USER() throws Exception {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(comment1.getContent());
        String request = objectMapper.writeValueAsString(createCommentRequest);
        CreateCommentResponse response = CreateCommentResponse.of(comment1);

        given(commentService.createComment(any(User.class), any(CreateCommentRequest.class), anyLong()))
                .willReturn(response);

        mvc.perform(post("/api/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void 댓글_작성한다_실패_내용이_빔() throws Exception{
        CreateCommentRequest createCommentRequest = new CreateCommentRequest("");
        String request = objectMapper.writeValueAsString(createCommentRequest);

        mvc.perform(post("/api/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser
    void 댓글_삭제한다_성공_USER() throws Exception{
        willDoNothing().given(commentService).deleteComment(any(User.class), anyLong());

        mvc.perform(delete("/api/1/comment/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("댓글을 삭제했습니다."));
    }


    @Test
    @WithMockCustomUser
    void 댓글_수정한다_성공_USER() throws Exception{
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(comment1.getContent());
        String request = objectMapper.writeValueAsString(updateCommentRequest);
        UpdateCommentResponse response = UpdateCommentResponse.of(comment1);

        given(commentService.updateComment(any(User.class), any(UpdateCommentRequest.class), anyLong()))
                .willReturn(response);

        mvc.perform(put("/api/1/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void 댓글_수정한다_실패_내용이_빔() throws Exception{
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("");
        String request = objectMapper.writeValueAsString(updateCommentRequest);

        mvc.perform(put("/api/1/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
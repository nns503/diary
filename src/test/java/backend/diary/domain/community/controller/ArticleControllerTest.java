package backend.diary.domain.community.controller;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(value = ArticleController.class)
class ArticleControllerTest {

    @Test
    void 게시글을_작성한다_성공_USER(){

    }

    @Test
    void 게시글을_작성한다_성공_ADMIN(){

    }

    @Test
    void 게시글을_작성한다_실패_인증받지_못한_회원(){

    }

    @Test
    void 게시글을_삭제한다_성공_USER(){

    }

    @Test
    void 게시글을_삭제한다_실패_작성자가_아님(){

    }

    @Test
    void 게시글을_수정한다_성공_USER(){

    }

    @Test
    void 게시글을_수정한다_실패_작성자가_아님(){

    }
}
package backend.diary.domain.community.fixture;

import backend.diary.domain.community.entity.Article;
import backend.diary.domain.user.entity.User;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleFixture {

    public Article 기본게시물1(User user){
        return Article.builder()
                .id(1L)
                .author(user.getNickname())
                .title("기본게시물1")
                .content("어떤 내용이 들어가는게 좋을까요? 1번 문장입니다.")
                .filePath("첫번째 파일 주소입니다")
                .isDeleted(false)
                .user(user)
                .build();
    }
}

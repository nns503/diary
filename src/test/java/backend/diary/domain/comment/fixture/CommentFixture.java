package backend.diary.domain.comment.fixture;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.user.entity.User;

@SuppressWarnings("NonAsciiCharacters")
public class CommentFixture {

    public Comment 기본댓글1(User user, Article article) {
        return Comment.builder()
                .id(1L)
                .author(user.getNickname())
                .content("첫번째 기본 댓글입니다.")
                .isDeleted(false)
                .user(user)
                .article(article)
                .build();
    }
}

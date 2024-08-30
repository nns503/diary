package backend.diary.domain.article.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record GetArticleDTO(
        long id,
        String title,
        String author,
        LocalDateTime createAt,
        int viewCount,
        int likeCount
) {

    @QueryProjection
    public GetArticleDTO(long id, String title, String author, LocalDateTime createAt, int viewCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createAt = createAt;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
}

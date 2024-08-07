package backend.diary.domain.comment.entity;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.user.entity.User;
import backend.diary.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public void update(String content){
        this.content = content;
    }

    public void delete(){
        this.isDeleted = true;
    }

    @Builder
    public Comment(Long id, String content, Article article, User user, Boolean isDeleted){
        this.id = id;
        this.content = content;
        this.article = article;
        this.user = user;
        this.isDeleted = isDeleted;
    }
}

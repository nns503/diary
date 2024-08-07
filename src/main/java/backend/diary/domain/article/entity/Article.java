package backend.diary.domain.article.entity;

import backend.diary.domain.user.entity.User;
import backend.diary.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
@Entity
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "like_Count", nullable = false)
    private Integer likeCount;

    @Builder
    public Article(Long id, String title, String content, User user, Boolean isDeleted, Integer likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.isDeleted = isDeleted;
        this.likeCount = likeCount;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void likeCountPlus(){
        likeCount++;
    }

    public void likeCountMinus(){
        likeCount--;
    }
}

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

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Article(Long id, String title, String content, String filePath, User user, Boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public void update(String title, String content, String filePath){
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    public void delete(){
        this.isDeleted = true;
    }
}

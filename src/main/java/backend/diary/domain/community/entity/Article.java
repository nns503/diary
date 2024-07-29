package backend.diary.domain.community.entity;

import backend.diary.domain.user.entity.User;
import backend.diary.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Article(Long id, String author, String title, String content, String filePath, User user, Boolean isDeleted) {
        this.id = id;
        this.author = author;
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

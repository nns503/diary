package backend.diary.global.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String refreshToken;
    private String expiration;

    @Builder
    public RefreshEntity(String username, String refreshToken, String expiration) {
        this.username = username;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}

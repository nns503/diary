package backend.diary.fixture;

import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.UserRole;

@SuppressWarnings("NonAsciiCharacters")
public class CommonUserFixture {

    public User 일반회원1 = User.builder()
            .id(1L)
            .nickname("일반회원1")
            .username("test1@naver.com")
            .password("test1234")
            .role(UserRole.ROLE_USER)
            .build();

    public User 일반회원2 = User.builder()
            .id(2L)
            .nickname("일반회원2")
            .username("test2@naver.com")
            .password("test4567")
            .role(UserRole.ROLE_USER)
            .build();

    public User 일반회원3 = User.builder()
            .id(3L)
            .nickname("일반회원3")
            .username("test3@naver.com")
            .password("test8910")
            .role(UserRole.ROLE_USER)
            .build();

    public User 관리자회원1 = User.builder()
            .id(4L)
            .nickname("관리자회원1")
            .username("admin1@naver.com")
            .password("admin1234")
            .role(UserRole.ROLE_ADMIN)
            .build();

    public User 관리자회원2 = User.builder()
            .id(5L)
            .nickname("관리자회원2")
            .username("admin2@naver.com")
            .password("admin5678")
            .role(UserRole.ROLE_ADMIN)
            .build();

    public static final User 관리자회원3 = User.builder()
            .id(6L)
            .nickname("관리자회원3")
            .username("admin3@naver.com")
            .password("admin8910")
            .role(UserRole.ROLE_ADMIN)
            .build();

}

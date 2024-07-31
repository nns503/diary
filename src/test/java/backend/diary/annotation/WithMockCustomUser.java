package backend.diary.annotation;

import backend.diary.domain.user.entity.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 1L;
    String nickname() default "테스트회원";
    String username() default "test1234@naver.com";
    String password() default "abcd1234";
    UserRole role() default UserRole.ROLE_USER;
}

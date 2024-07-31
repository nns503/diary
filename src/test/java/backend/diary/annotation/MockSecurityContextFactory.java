package backend.diary.annotation;

import backend.diary.domain.user.entity.CustomUserDetails;
import backend.diary.domain.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        User user = new User(annotation.id(), annotation.nickname(), annotation.username(), annotation.password(), annotation.role());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}

package backend.diary.common;

import backend.diary.global.auth.entity.repository.RefreshRepository;
import backend.diary.global.auth.exception.JwtAccessDeniedHandler;
import backend.diary.global.auth.exception.JwtAuthenticationEntryPoint;
import backend.diary.global.auth.jwt.JWTUtil;
import backend.diary.global.config.SecurityAppConfig;
import backend.diary.global.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

@Import({SecurityConfig.class, SecurityAppConfig.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected RefreshRepository refreshRepository;

    @MockBean
    protected AuthenticationConfiguration authenticationConfiguration;
    @MockBean
    protected JWTUtil jwtUtil;
    @MockBean
    protected UserDetailsService userDetailsService;

    @SpyBean
    protected JwtAuthenticationEntryPoint authenticationEntryPoint;
    @SpyBean
    protected JwtAccessDeniedHandler accessDeniedHandler;
}

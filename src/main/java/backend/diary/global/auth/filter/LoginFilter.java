    package backend.diary.global.auth.filter;

    import backend.diary.domain.user.entity.CustomUserDetails;
    import backend.diary.domain.user.entity.UserRole;
    import backend.diary.global.auth.dto.LoginDTO;
    import backend.diary.global.auth.entity.RefreshEntity;
    import backend.diary.global.auth.entity.repository.RefreshRepository;
    import backend.diary.global.auth.jwt.JWTUtil;
    import backend.diary.global.utill.ResponseUtil;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.ServletInputStream;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.util.StreamUtils;

    import java.io.IOException;
    import java.nio.charset.StandardCharsets;
    import java.util.Collection;
    import java.util.Iterator;

    @RequiredArgsConstructor
    public class LoginFilter extends UsernamePasswordAuthenticationFilter {

        private final AuthenticationManager authenticationManager;
        private final JWTUtil jwtUtil;
        private final RefreshRepository refreshRepository;

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            LoginDTO loginDTO = new LoginDTO();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ServletInputStream inputStream = request.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                loginDTO = objectMapper.readValue(messageBody, LoginDTO.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authToken);
        }


        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String username = customUserDetails.getUsername();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            GrantedAuthority auth = iterator.next();
            String role = auth.getAuthority();

            String accessToken = jwtUtil.createAccessToken(username, UserRole.valueOf(role));
            String refreshToken = jwtUtil.createRefreshToken(username, UserRole.valueOf(role));

            addRefreshEntity(username, refreshToken);

            response.setHeader("Authorization", "Bearer " + accessToken);
            response.addCookie(createCookie("refresh", refreshToken));
            response.setStatus(HttpStatus.OK.value());
        }

        private void addRefreshEntity(String username, String refreshToken) {
            RefreshEntity refreshEntity = RefreshEntity.builder()
                    .username(username)
                    .refreshToken(refreshToken)
                    .expiration(String.valueOf(jwtUtil.getExpiration(refreshToken)))
                    .build();

            refreshRepository.save(refreshEntity);
        }

        private Cookie createCookie(String key, String value) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge((int)jwtUtil.getRefreshExpirationDuration());
            cookie.setHttpOnly(true);

            return cookie;
        }

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
            ResponseUtil.writeJsonResponse(response, HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호를 찾을 수 없습니다.");
        }

    }

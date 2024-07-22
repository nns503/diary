package backend.diary.global.auth.filter;

import backend.diary.domain.user.entity.User;
import backend.diary.global.auth.exception.CustomJwtException;
import backend.diary.global.auth.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            // 인증이 필요 없는 요청일 수 있으니 다음으로 넘김
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = authorization.substring(7);

        if(!jwtUtil.validationToken(accessToken)){
            throw new CustomJwtException();
        }

        String category = jwtUtil.getCategory(accessToken);

        if(!category.equals("access")) {
            throw new CustomJwtException("Access Token이 아닙니다.");
        }

        String username = jwtUtil.getUsername(accessToken);
        UserDetails userDetails = (User)userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

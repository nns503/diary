package backend.diary.global.auth.controller;

import backend.diary.domain.user.entity.UserRole;
import backend.diary.global.auth.entity.RefreshEntity;
import backend.diary.global.auth.entity.repository.RefreshRepository;
import backend.diary.global.auth.exception.CustomJwtException;
import backend.diary.global.auth.exception.RefreshTokenNullException;
import backend.diary.global.auth.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if(refresh == null) {
            throw new RefreshTokenNullException();
        }

        if(!jwtUtil.validationToken(refresh)){
            throw new CustomJwtException();
        }

        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            throw new CustomJwtException("리프레쉬 토큰이 아닙니다.");
        }

        if(!refreshRepository.existsByRefreshToken(refresh)){
            throw new CustomJwtException("저장되지 않은 리프레쉬 토큰입니다.");
        }

        String username = jwtUtil.getUsername(refresh);
        UserRole role = jwtUtil.getRole(refresh);

        String newAccessToken = jwtUtil.createAccessToken(username, role);
        String newRefreshToken = jwtUtil.createRefreshToken(username, role);

        refreshRepository.deleteByRefreshToken(refresh);
        addRefreshEntity(username, newRefreshToken);

        response.setHeader("Authorization", newAccessToken);
        response.addCookie(createCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int)jwtUtil.getRefreshExpirationDuration());
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refreshToken) {
        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(username)
                .refreshToken(refreshToken)
                .expiration(String.valueOf(jwtUtil.getExpiration(refreshToken)))
                .build();

        refreshRepository.save(refreshEntity);
    }
}

package backend.diary.global.auth.controller;

import backend.diary.domain.user.entity.UserRole;
import backend.diary.global.auth.entity.RefreshEntity;
import backend.diary.global.auth.entity.repository.RefreshRepository;
import backend.diary.global.auth.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
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
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try{
            jwtUtil.isExpired(refresh);
        }catch(ExpiredJwtException e){
            System.out.println("access Token expired Exception!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            return new ResponseEntity<>("refresh token is invalid", HttpStatus.BAD_REQUEST);
        }

        if(!refreshRepository.existsByRefreshToken(refresh)){
            return new ResponseEntity<>("refresh token doesn't exist", HttpStatus.BAD_REQUEST);
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

package backend.diary.global.auth.jwt;

import backend.diary.domain.user.entity.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.expiration}")
    private long accessExpiration;
    @Value("${spring.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String createRefreshToken(String username, UserRole role){
        return Jwts.builder()
                .claim("category", "refresh")
                .claim("username", username)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public String createAccessToken(String username, UserRole role){
        return Jwts.builder()
                .claim("category", "access")
                .claim("username", username)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public UserRole getRole(String token){
        String roleString = Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return UserRole.valueOf(roleString);
    }

    public Date getExpiration(String token){
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Boolean isExpired(String token){
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public long getRefreshExpirationDuration(){
        return refreshExpiration;
    }

    public String getCategory(String token){
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("category", String.class);
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

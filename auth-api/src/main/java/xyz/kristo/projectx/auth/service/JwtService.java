package xyz.kristo.projectx.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.kristo.projectx.auth.model.JwtContext;
import xyz.kristo.projectx.auth.util.DateConverter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {
    @Value("${authentication.jwtSecret}")
    private String jwtSecret;

    public JwtContext verify(String token) throws JwtException {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = jwt.getBody();

        return new JwtContext(
                claims.get("id", Long.class),
                claims.get("username", String.class),
                claims.get("email", String.class),
                DateConverter.asLocalDateTime(claims.getExpiration())
        );
    }

    public JwtContext create(Long accountId, String username, String email) {
        return new JwtContext(
                accountId,
                username,
                email,
                LocalDateTime.now().plusHours(1)
        );
    }

    public String encode(JwtContext context) {
        return Jwts.builder()
                .setSubject(context.getUsername())
                .setExpiration(DateConverter.asDate(context.getExpiryTime()))
                .setIssuedAt(new Date())
                .claim("id", context.getAccountId())
                .claim("username", context.getUsername())
                .claim("email", context.getEmail())
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}

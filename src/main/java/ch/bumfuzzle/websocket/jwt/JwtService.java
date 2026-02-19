package ch.bumfuzzle.websocket.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

  private final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
  private final String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(
        base64Key.getBytes(StandardCharsets.UTF_8)
    );
  }

  public boolean isValid(final String token) {
    try {
      Jwts.parser()
          .verifyWith(getKey())
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (final Exception _) {
      return false;
    }
  }

  public String extractUsername(final String token) {
    final Claims claims = Jwts.parser()
                              .verifyWith(getKey())
                              .build()
                              .parseSignedClaims(token)
                              .getPayload();

    return claims.getSubject();
  }

  public String generateToken(final String username) {

    final long now = System.currentTimeMillis();

    return Jwts.builder()
               .subject(username)
               .issuedAt(new Date(now))
               .expiration(new Date(now + 1000 * 60 * 60)) // 1h
               .signWith(getKey())
               .compact();
  }
}


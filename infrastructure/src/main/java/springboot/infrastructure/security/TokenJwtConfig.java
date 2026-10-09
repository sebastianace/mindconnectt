package springboot.infrastructure.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Configuración y utilidades del JWT.
 *
 * Diferencia con el taller: allí la clave se genera con Jwts.SIG.HS256.key().build() y cambia en cada
 * arranque, así que todos los tokens mueren al reiniciar. Aquí la clave viene de la variable de entorno
 * JWT_SECRET (Base64, mínimo 256 bits), leída con @Value, y nunca se escribe en el código.
 */
@Component
public class TokenJwtConfig {
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final String AUTHORITIES_CLAIM = "authorities";

    private final SecretKey secretKey;
    private final long expirationMs;

    public TokenJwtConfig(
            @Value("${security.jwt.secret}") String base64Secret,
            @Value("${security.jwt.expiration-ms:3600000}") long expirationMs) {
        this.secretKey = buildKey(base64Secret);
        this.expirationMs = expirationMs;
    }

    private static SecretKey buildKey(String base64Secret) {
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                    "JWT_SECRET must be a Base64 string of at least 256 bits (32 bytes). "
                            + "Generate one with: openssl rand -base64 48", ex);
        }
    }

    public String generateToken(String username, Collection<String> authorities) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim(AUTHORITIES_CLAIM, List.copyOf(authorities))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    /** Verifica firma y expiración. Lanza io.jsonwebtoken.JwtException si el token no es válido. */
    public Claims parse(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public long expirationMs() {
        return expirationMs;
    }
}

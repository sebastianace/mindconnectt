package springboot.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

/** Prueba unitaria pura (sin Spring ni base de datos): firma, expiración y manipulación del JWT. */
class TokenJwtConfigTest {
    private static String newSecret() {
        return Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
    }

    @Test void shouldIssueAndParseTokenWithUsernameAndRoles() {
        TokenJwtConfig config = new TokenJwtConfig(newSecret(), 60_000);

        String token = config.generateToken("sebastian", List.of("ROLE_USER", "ROLE_ADMIN"));
        Claims claims = config.parse(token);

        assertEquals("sebastian", claims.getSubject());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), claims.get(TokenJwtConfig.AUTHORITIES_CLAIM, List.class));
    }

    @Test void shouldRejectExpiredToken() {
        TokenJwtConfig config = new TokenJwtConfig(newSecret(), -1_000);

        String token = config.generateToken("sebastian", List.of("ROLE_USER"));

        assertThrows(JwtException.class, () -> config.parse(token));
    }

    @Test void shouldRejectTokenSignedWithAnotherKey() {
        String token = new TokenJwtConfig(newSecret(), 60_000).generateToken("sebastian", List.of("ROLE_USER"));

        assertThrows(JwtException.class, () -> new TokenJwtConfig(newSecret(), 60_000).parse(token));
    }

    @Test void shouldRejectTamperedToken() {
        TokenJwtConfig config = new TokenJwtConfig(newSecret(), 60_000);
        String token = config.generateToken("sebastian", List.of("ROLE_USER"));

        assertThrows(JwtException.class, () -> config.parse(token.substring(0, token.length() - 3) + "abc"));
    }

    @Test void shouldKeepValidatingAfterRestartWhenSecretIsTheSame() {
        String secret = newSecret();
        String token = new TokenJwtConfig(secret, 60_000).generateToken("sebastian", List.of("ROLE_USER"));

        assertEquals("sebastian", new TokenJwtConfig(secret, 60_000).parse(token).getSubject());
    }

    @Test void shouldFailFastWithAClearMessageWhenSecretIsTooShort() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> new TokenJwtConfig(Encoders.BASE64.encode("corta".getBytes()), 60_000));

        assertTrue(ex.getMessage().contains("JWT_SECRET"));
    }
}

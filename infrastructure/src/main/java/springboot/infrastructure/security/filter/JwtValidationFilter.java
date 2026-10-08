package springboot.infrastructure.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springboot.infrastructure.security.TokenJwtConfig;

/**
 * Filtro de VALIDACIÓN: en cada petición lee "Authorization: Bearer <token>", verifica firma y
 * expiración y deja al usuario autenticado en el SecurityContext. El taller de la guía llega hasta
 * generar el token; sin este filtro el token nunca se aceptaría en los demás endpoints.
 *
 * Si el token falta o es inválido NO responde aquí: deja la petición sin autenticar, de modo que las
 * rutas públicas siguen funcionando y las protegidas devuelven 401 por el AuthenticationEntryPoint.
 */
public class JwtValidationFilter extends OncePerRequestFilter {
    private final TokenJwtConfig tokenConfig;

    public JwtValidationFilter(TokenJwtConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header != null && header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            authenticate(header.substring(TokenJwtConfig.PREFIX_TOKEN.length()).trim());
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        try {
            Claims claims = tokenConfig.parse(token);
            String username = claims.getSubject();
            if (username == null || username.isBlank()) {
                return;
            }
            List<GrantedAuthority> authorities = extractAuthorities(claims);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(username, null, authorities));
            SecurityContextHolder.setContext(context);
        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
        }
    }

    private static List<GrantedAuthority> extractAuthorities(Claims claims) {
        Object raw = claims.get(TokenJwtConfig.AUTHORITIES_CLAIM);
        if (!(raw instanceof List<?> values)) {
            return List.of();
        }
        return values.stream()
                .filter(String.class::isInstance)
                .map(value -> (GrantedAuthority) new SimpleGrantedAuthority((String) value))
                .toList();
    }
}

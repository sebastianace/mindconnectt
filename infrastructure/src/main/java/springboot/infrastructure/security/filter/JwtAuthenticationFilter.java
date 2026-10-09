package springboot.infrastructure.security.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springboot.infrastructure.security.SecurityProblemWriter;
import springboot.infrastructure.security.TokenJwtConfig;

/**
 * Filtro de LOGIN (POST /api/auth/login con {"username","password"}). Igual que en el taller extiende
 * UsernamePasswordAuthenticationFilter: delega la verificación en el AuthenticationManager y, si las
 * credenciales son correctas, responde con el JWT.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String LOGIN_URL = "/api/auth/login";

    private final TokenJwtConfig tokenConfig;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenJwtConfig tokenConfig) {
        super(authenticationManager);
        this.tokenConfig = tokenConfig;
        setFilterProcessesUrl(LOGIN_URL);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LoginRequest(String username, String password) {
        @Override
        public String toString() {
            return "LoginRequest[username=" + username + "]";
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        LoginRequest login;
        try {
            login = SecurityProblemWriter.read(request.getInputStream(), LoginRequest.class);
        } catch (RuntimeException | IOException ex) {
            throw new BadCredentialsException("Invalid login request body");
        }
        if (login == null || login.username() == null || login.username().isBlank()
                || login.password() == null || login.password().isEmpty()) {
            throw new BadCredentialsException("username and password are required");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.unauthenticated(login.username(), login.password());
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails) authResult.getPrincipal();
        String username = user.getUsername();
        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();

        String token = tokenConfig.generateToken(username, roles);

        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("token", token);
        body.put("type", "Bearer");
        body.put("username", username);
        body.put("roles", roles);
        body.put("expiresInMs", tokenConfig.expirationMs());
        body.put("message", String.format("Hola %s ha iniciado sesion con exito!", username));
        SecurityProblemWriter.writeJson(response, HttpStatus.OK.value(), body);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        // Mensaje único a propósito: no revela si falló el usuario, la clave o si la cuenta está deshabilitada.
        SecurityProblemWriter.writeProblem(request, response, HttpStatus.UNAUTHORIZED, "Authentication failed",
                "Error en la autenticacion: username o password incorrectos");
    }
}

package springboot.infrastructure.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** 401: la petición llegó sin token, con token inválido o vencido. */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        SecurityProblemWriter.writeProblem(request, response, HttpStatus.UNAUTHORIZED, "Unauthorized",
                "Authentication is required: send a valid 'Authorization: Bearer <token>' header");
    }
}

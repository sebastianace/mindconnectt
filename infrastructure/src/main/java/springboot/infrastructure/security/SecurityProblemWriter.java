package springboot.infrastructure.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.json.JsonMapper;

/**
 * Escribe respuestas JSON desde los filtros de seguridad, que se ejecutan ANTES de Spring MVC
 * y por eso no pasan por el GlobalExceptionHandler. Mantiene el mismo formato ProblemDetail (RFC 9457).
 */
public final class SecurityProblemWriter {
    private static final JsonMapper MAPPER = JsonMapper.builder().build();

    private SecurityProblemWriter() {
    }

    public static void writeProblem(HttpServletRequest request, HttpServletResponse response,
            HttpStatus status, String title, String detail) throws IOException {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", "about:blank");
        body.put("title", title);
        body.put("status", status.value());
        body.put("detail", detail);
        body.put("instance", request.getRequestURI());
        write(response, status.value(), "application/problem+json", body);
    }

    public static void writeJson(HttpServletResponse response, int status, Object body) throws IOException {
        write(response, status, TokenJwtConfig.CONTENT_TYPE, body);
    }

    private static void write(HttpServletResponse response, int status, String contentType, Object body)
            throws IOException {
        response.setStatus(status);
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(MAPPER.writeValueAsString(body));
    }

    public static <T> T read(java.io.InputStream input, Class<T> type) {
        return MAPPER.readValue(input, type);
    }
}

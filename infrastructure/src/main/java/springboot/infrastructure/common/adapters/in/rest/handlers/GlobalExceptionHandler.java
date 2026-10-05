package springboot.infrastructure.common.adapters.in.rest.handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.common.exception.NotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.common.exception.DomainValidationException;

/**
 * Traduce las excepciones de dominio, aplicación y persistencia a respuestas HTTP
 * con formato estándar RFC 9457 (ProblemDetail) para los 52 controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final HttpStatusCode UNPROCESSABLE = HttpStatusCode.valueOf(422);

    @ExceptionHandler(NotFoundApplicationException.class)
    public ProblemDetail handleNotFound(NotFoundApplicationException ex) {
        return problem(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    @ExceptionHandler(ReferenceNotFoundApplicationException.class)
    public ProblemDetail handleReferenceNotFound(ReferenceNotFoundApplicationException ex) {
        return problem(UNPROCESSABLE, "Referenced resource not found", ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceApplicationException.class)
    public ProblemDetail handleDuplicate(DuplicateResourceApplicationException ex) {
        return problem(HttpStatus.CONFLICT, "Duplicate resource", ex.getMessage());
    }

    @ExceptionHandler(DomainValidationException.class)
    public ProblemDetail handleDomainValidation(DomainValidationException ex) {
        return problem(HttpStatus.BAD_REQUEST, "Business rule violated", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail problem = problem(HttpStatus.BAD_REQUEST, "Invalid request", "One or more fields are invalid");
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableBody(HttpMessageNotReadableException ex) {
        return problem(HttpStatus.BAD_REQUEST, "Malformed request body",
                "The request body is missing or is not valid JSON");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return problem(HttpStatus.BAD_REQUEST, "Invalid parameter",
                "Parameter '" + ex.getName() + "' has an invalid value: " + ex.getValue());
    }

    /**
     * Respaldo para restricciones de la base de datos (UNIQUE, FOREIGN KEY, CHECK).
     * Nunca se expone el mensaje SQL al cliente; solo se registra en el log.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        String cause = String.valueOf(ex.getMostSpecificCause().getMessage());
        log.warn("Data integrity violation: {}", cause);
        if (cause.contains("Cannot delete or update a parent row")) {
            return problem(HttpStatus.CONFLICT, "Resource in use",
                    "The resource cannot be deleted because other records reference it");
        }
        if (cause.contains("Duplicate entry")) {
            return problem(HttpStatus.CONFLICT, "Duplicate resource", "A record with the same unique value already exists");
        }
        if (cause.contains("Check constraint")) {
            return problem(HttpStatus.BAD_REQUEST, "Business rule violated", "The data violates a database check constraint");
        }
        if (cause.contains("foreign key constraint fails")) {
            return problem(UNPROCESSABLE, "Referenced resource not found", "A referenced record does not exist");
        }
        return problem(HttpStatus.CONFLICT, "Data integrity violation", "The operation violates a database constraint");
    }

    private static ProblemDetail problem(HttpStatusCode status, String title, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        return problem;
    }
}

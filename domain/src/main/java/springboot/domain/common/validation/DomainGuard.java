package springboot.domain.common.validation;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

import springboot.domain.common.exception.DomainValidationException;

/**
 * Validaciones reutilizables para proteger las invariantes de los agregados.
 * Es Java puro: no depende de Spring ni de Jakarta Validation.
 */
public final class DomainGuard {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private DomainGuard() {
    }

    public static String requireText(String value, String field) {
        Objects.requireNonNull(value, field + " must not be null");
        if (value.isBlank()) {
            throw new DomainValidationException(field + " must not be blank");
        }
        return value;
    }

    public static String requireEmail(String value, String field) {
        requireText(value, field);
        if (!EMAIL.matcher(value).matches()) {
            throw new DomainValidationException(field + " must be a valid email");
        }
        return value;
    }

    public static int requireNonNegative(int value, String field) {
        if (value < 0) {
            throw new DomainValidationException(field + " must be greater than or equal to 0");
        }
        return value;
    }

    public static int requirePositive(int value, String field) {
        if (value <= 0) {
            throw new DomainValidationException(field + " must be greater than 0");
        }
        return value;
    }

    public static BigDecimal requireNonNegative(BigDecimal value, String field) {
        Objects.requireNonNull(value, field + " must not be null");
        if (value.signum() < 0) {
            throw new DomainValidationException(field + " must be greater than or equal to 0");
        }
        return value;
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new DomainValidationException(message);
        }
    }
}

package springboot.domain.common.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import springboot.domain.common.exception.DomainValidationException;

class DomainGuardTest {
    @Test void shouldRejectNullAndBlankText() {
        assertThrows(NullPointerException.class, () -> DomainGuard.requireText(null, "name"));
        DomainValidationException ex = assertThrows(DomainValidationException.class,
                () -> DomainGuard.requireText("   ", "name"));
        assertEquals("name must not be blank", ex.getMessage());
        assertEquals("Colombia", DomainGuard.requireText("Colombia", "name"));
    }

    @Test void shouldValidateEmailFormat() {
        assertEquals("ana@example.com", DomainGuard.requireEmail("ana@example.com", "email"));
        assertThrows(DomainValidationException.class, () -> DomainGuard.requireEmail("ana.example.com", "email"));
        assertThrows(DomainValidationException.class, () -> DomainGuard.requireEmail("ana@example", "email"));
    }

    @Test void shouldValidateNumbers() {
        assertEquals(0, DomainGuard.requireNonNegative(0, "tokens"));
        assertThrows(DomainValidationException.class, () -> DomainGuard.requireNonNegative(-1, "tokens"));
        assertThrows(DomainValidationException.class, () -> DomainGuard.requirePositive(0, "maxTokens"));
        assertThrows(DomainValidationException.class,
                () -> DomainGuard.requireNonNegative(new BigDecimal("-0.01"), "cost"));
    }
}

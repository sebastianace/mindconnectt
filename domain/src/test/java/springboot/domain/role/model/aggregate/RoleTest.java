package springboot.domain.role.model.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.role.model.valueobject.RoleId;

class RoleTest {
    @Test void shouldRestoreRoleWithSpringSecurityPrefix() {
        Role role = Role.restore(RoleId.generate(), Role.ADMIN, "Administrador", LocalDateTime.now());

        assertEquals("ROLE_ADMIN", role.name());
    }

    @Test void shouldRejectNameWithoutRolePrefix() {
        assertThrows(DomainValidationException.class,
                () -> Role.restore(RoleId.generate(), "ADMIN", "Administrador", LocalDateTime.now()));
    }
}

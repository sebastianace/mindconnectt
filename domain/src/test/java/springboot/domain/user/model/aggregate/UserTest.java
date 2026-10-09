package springboot.domain.user.model.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.user.event.UserRegisteredEvent;

class UserTest {
    private static final String HASH = "$2a$10$abcdefghijklmnopqrstuuABCDEFGHIJKLMNOPQRSTUVWXYZ0123";

    @Test void shouldRegisterEnabledUserWithEvent() {
        RoleId role = RoleId.generate();

        User user = User.register("sebastian", HASH, Set.of(role));

        assertEquals("sebastian", user.username());
        assertEquals(HASH, user.passwordHash());
        assertTrue(user.enabled());
        assertEquals(Set.of(role), user.roleIds());
        assertEquals(1, user.domainEvents().size());
        assertInstanceOf(UserRegisteredEvent.class, user.domainEvents().getFirst());
    }

    @Test void shouldRejectUsernameThatIsTooShort() {
        assertThrows(DomainValidationException.class,
                () -> User.register("ab", HASH, Set.of(RoleId.generate())));
    }

    @Test void shouldRejectUsernameWithSpacesOrSymbols() {
        assertThrows(DomainValidationException.class,
                () -> User.register("juan perez", HASH, Set.of(RoleId.generate())));
        assertThrows(DomainValidationException.class,
                () -> User.register("juan@perez", HASH, Set.of(RoleId.generate())));
    }

    @Test void shouldRejectBlankPasswordHash() {
        assertThrows(DomainValidationException.class,
                () -> User.register("sebastian", "  ", Set.of(RoleId.generate())));
    }

    @Test void shouldRequireAtLeastOneRole() {
        assertThrows(DomainValidationException.class, () -> User.register("sebastian", HASH, Set.of()));
    }
}

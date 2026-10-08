package springboot.application.user.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.user.command.RegisterUserCommand;
import springboot.application.user.dto.UserResponse;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.event.UserRegisteredEvent;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.model.valueobject.UserId;
import springboot.domain.user.port.repository.UserRepository;

class RegisterUserUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();
    private final Role userRole = Role.restore(RoleId.generate(), Role.USER, "Usuario", LocalDateTime.now());
    private final Role adminRole = Role.restore(RoleId.generate(), Role.ADMIN, "Admin", LocalDateTime.now());
    private final InMemoryUsers users = new InMemoryUsers();
    private final RegisterUserUseCase useCase = new RegisterUserUseCase(
            users, new InMemoryRoles(userRole, adminRole), raw -> "hashed:" + raw, published::addAll);

    @Test void shouldRegisterWithRoleUserHashedPasswordAndEvent() {
        UserResponse response = useCase.execute(new RegisterUserCommand("sebastian", "Clave12345", false));

        assertEquals("sebastian", response.username());
        assertEquals(List.of("ROLE_USER"), response.roles());
        assertEquals("hashed:Clave12345", users.saved.getFirst().passwordHash(),
                "only the hash may be stored, never the plain password");
        assertEquals(1, published.size());
        assertInstanceOf(UserRegisteredEvent.class, published.getFirst());
    }

    @Test void shouldAddRoleAdminWhenRequested() {
        UserResponse response = useCase.execute(new RegisterUserCommand("admin01", "Clave12345", true));

        assertEquals(List.of("ROLE_ADMIN", "ROLE_USER"), response.roles());
    }

    @Test void shouldRejectDuplicateUsername() {
        useCase.execute(new RegisterUserCommand("sebastian", "Clave12345", false));

        DuplicateResourceApplicationException ex = assertThrows(DuplicateResourceApplicationException.class,
                () -> useCase.execute(new RegisterUserCommand("sebastian", "OtraClave123", false)));

        assertEquals("User with username 'sebastian' already exists", ex.getMessage());
    }

    @Test void shouldRejectShortPassword() {
        assertThrows(DomainValidationException.class,
                () -> useCase.execute(new RegisterUserCommand("sebastian", "corta", false)));
        assertTrue(users.saved.isEmpty());
        assertTrue(published.isEmpty());
    }

    @Test void shouldRejectPasswordLongerThanBcryptLimit() {
        assertThrows(DomainValidationException.class,
                () -> useCase.execute(new RegisterUserCommand("sebastian", "a".repeat(73), false)));
    }

    @Test void shouldNotLeakPasswordInCommandToString() {
        String text = new RegisterUserCommand("sebastian", "Clave12345", false).toString();

        assertFalse(text.contains("Clave12345"));
    }

    private static final class InMemoryUsers implements UserRepository {
        private final List<User> saved = new ArrayList<>();
        @Override public User save(User aggregate) { saved.add(aggregate); return aggregate; }
        @Override public Optional<User> findById(UserId id) { return saved.stream().filter(u -> u.id().equals(id)).findFirst(); }
        @Override public Optional<User> findByUsername(String username) { return saved.stream().filter(u -> u.username().equals(username)).findFirst(); }
        @Override public List<User> findAll() { return List.copyOf(saved); }
        @Override public boolean existsByUsername(String username) { return findByUsername(username).isPresent(); }
    }

    private static final class InMemoryRoles implements RoleRepository {
        private final List<Role> roles;
        private InMemoryRoles(Role... roles) { this.roles = List.of(roles); }
        @Override public Optional<Role> findByName(String name) { return roles.stream().filter(r -> r.name().equals(name)).findFirst(); }
        @Override public List<Role> findAllById(Collection<RoleId> ids) { return roles.stream().filter(r -> ids.contains(r.id())).toList(); }
    }
}

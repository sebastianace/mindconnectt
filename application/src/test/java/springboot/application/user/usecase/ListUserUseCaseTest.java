package springboot.application.user.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import springboot.application.user.dto.UserResponse;
import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.model.valueobject.UserId;
import springboot.domain.user.port.repository.UserRepository;

class ListUserUseCaseTest {
    @Test void shouldListUsersWithRoleNamesAndWithoutPasswords() {
        Role userRole = Role.restore(RoleId.generate(), Role.USER, "Usuario", LocalDateTime.now());
        Role adminRole = Role.restore(RoleId.generate(), Role.ADMIN, "Admin", LocalDateTime.now());
        User admin = User.register("admin01", "hash", Set.of(userRole.id(), adminRole.id()));
        User normal = User.register("sebastian", "hash", Set.of(userRole.id()));

        List<UserResponse> result = new ListUserUseCase(
                new UsersFixed(List.of(admin, normal)), new RolesFixed(List.of(userRole, adminRole))).execute();

        assertEquals(2, result.size());
        assertEquals(List.of("ROLE_ADMIN", "ROLE_USER"), result.get(0).roles());
        assertEquals(List.of("ROLE_USER"), result.get(1).roles());
        assertFalse(result.toString().contains("hash"), "response must not expose the password hash");
    }

    private record UsersFixed(List<User> users) implements UserRepository {
        @Override public User save(User aggregate) { return aggregate; }
        @Override public Optional<User> findById(UserId id) { return users.stream().filter(u -> u.id().equals(id)).findFirst(); }
        @Override public Optional<User> findByUsername(String username) { return users.stream().filter(u -> u.username().equals(username)).findFirst(); }
        @Override public List<User> findAll() { return users; }
        @Override public boolean existsByUsername(String username) { return findByUsername(username).isPresent(); }
    }

    private record RolesFixed(List<Role> roles) implements RoleRepository {
        @Override public Optional<Role> findByName(String name) { return roles.stream().filter(r -> r.name().equals(name)).findFirst(); }
        @Override public List<Role> findAllById(Collection<RoleId> ids) { return roles.stream().filter(r -> ids.contains(r.id())).toList(); }
    }
}

package springboot.application.user.usecase;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.user.command.RegisterUserCommand;
import springboot.application.user.dto.UserResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.port.repository.UserRepository;
import springboot.domain.user.port.security.PasswordHasher;

/**
 * Registra un usuario: valida la contraseña, verifica que el username sea único, asigna
 * ROLE_USER (y ROLE_ADMIN si se pide) y guarda solo el hash de la contraseña.
 */
public class RegisterUserUseCase {
    static final int MIN_PASSWORD_LENGTH = 8;
    /** BCrypt solo procesa los primeros 72 bytes: más largo se rechaza en vez de truncar en silencio. */
    static final int MAX_PASSWORD_BYTES = 72;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final DomainEventPublisher eventPublisher;

    public RegisterUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordHasher passwordHasher,
            DomainEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
        this.eventPublisher = eventPublisher;
    }

    public UserResponse execute(RegisterUserCommand command) {
        DomainGuard.require(command.rawPassword().length() >= MIN_PASSWORD_LENGTH,
                "password must have at least " + MIN_PASSWORD_LENGTH + " characters");
        DomainGuard.require(command.rawPassword().getBytes(StandardCharsets.UTF_8).length <= MAX_PASSWORD_BYTES,
                "password must be at most " + MAX_PASSWORD_BYTES + " bytes long");

        if (userRepository.existsByUsername(command.username())) {
            throw new DuplicateResourceApplicationException("User", "username", command.username());
        }

        List<Role> roles = resolveRoles(command.admin());
        Set<RoleId> roleIds = new HashSet<>();
        roles.forEach(role -> roleIds.add(role.id()));

        User aggregate = User.register(
                command.username(),
                passwordHasher.hash(command.rawPassword()),
                roleIds);
        User saved = userRepository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return UserResponse.from(saved, roles.stream().map(Role::name).sorted().toList());
    }

    private List<Role> resolveRoles(boolean admin) {
        List<Role> roles = new ArrayList<>();
        roles.add(requireRole(Role.USER));
        if (admin) {
            roles.add(requireRole(Role.ADMIN));
        }
        return roles;
    }

    private Role requireRole(String name) {
        return roleRepository.findByName(name).orElseThrow(() ->
                new IllegalStateException("Base role " + name + " is missing; run the Flyway migrations (V56)"));
    }
}

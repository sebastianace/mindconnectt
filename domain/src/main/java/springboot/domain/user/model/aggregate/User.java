package springboot.domain.user.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import springboot.domain.common.model.AggregateRoot;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.user.event.UserRegisteredEvent;
import springboot.domain.user.model.valueobject.UserId;

/**
 * Usuario del sistema. El agregado nunca conoce la contraseña en texto plano: recibe
 * el hash ya calculado (puerto PasswordHasher). Referencia sus roles por identidad (RoleId),
 * igual que el resto de agregados del proyecto.
 */
public class User extends AggregateRoot {
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9._-]{3,50}$");

    private final UserId id;
    private final String username;
    private final String passwordHash;
    private boolean enabled;
    private final Set<RoleId> roleIds;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(
            UserId id,
            String username,
            String passwordHash,
            boolean enabled,
            Set<RoleId> roleIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.username = DomainGuard.requireText(username, "username");
        DomainGuard.require(USERNAME.matcher(username).matches(),
                "username must have 3 to 50 characters (letters, numbers, '.', '_' or '-')");
        this.passwordHash = DomainGuard.requireText(passwordHash, "passwordHash");
        this.enabled = enabled;
        Objects.requireNonNull(roleIds, "roleIds must not be null");
        DomainGuard.require(!roleIds.isEmpty(), "user must have at least one role");
        this.roleIds = Set.copyOf(roleIds);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static User register(String username, String passwordHash, Set<RoleId> roleIds) {
        UserId id = UserId.generate();
        LocalDateTime now = LocalDateTime.now();
        User aggregate = new User(id, username, passwordHash, true, roleIds, now, now);
        aggregate.recordEvent(new UserRegisteredEvent(id, aggregate.username, now));
        return aggregate;
    }

    public static User restore(
            UserId id,
            String username,
            String passwordHash,
            boolean enabled,
            Set<RoleId> roleIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new User(id, username, passwordHash, enabled, roleIds, createdAt, updatedAt);
    }

    public UserId id() { return id; }
    public String username() { return username; }
    public String passwordHash() { return passwordHash; }
    public boolean enabled() { return enabled; }
    public Set<RoleId> roleIds() { return roleIds; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

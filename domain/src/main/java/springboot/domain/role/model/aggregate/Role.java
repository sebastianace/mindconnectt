package springboot.domain.role.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.common.model.AggregateRoot;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.role.model.valueobject.RoleId;

/**
 * Rol de seguridad (catálogo). Por convención de Spring Security el nombre siempre
 * empieza con "ROLE_" (ROLE_USER, ROLE_ADMIN). Los roles base los siembra la migración V56.
 */
public class Role extends AggregateRoot {
    public static final String PREFIX = "ROLE_";
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";

    private final RoleId id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;

    private Role(RoleId id, String name, String description, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = DomainGuard.requireText(name, "name");
        DomainGuard.require(name.startsWith(PREFIX), "name must start with " + PREFIX);
        this.description = DomainGuard.requireText(description, "description");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static Role restore(RoleId id, String name, String description, LocalDateTime createdAt) {
        return new Role(id, name, description, createdAt);
    }

    public RoleId id() { return id; }
    public String name() { return name; }
    public String description() { return description; }
    public LocalDateTime createdAt() { return createdAt; }
}

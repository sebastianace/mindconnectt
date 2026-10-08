package springboot.domain.role.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record RoleId(UUID value) {
    public RoleId {
        Objects.requireNonNull(value, "RoleId value must not be null");
    }

    public static RoleId generate() {
        return new RoleId(UUID.randomUUID());
    }
}

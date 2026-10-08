package springboot.application.user.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import springboot.domain.user.model.aggregate.User;

/** Nunca incluye la contraseña ni su hash. */
public record UserResponse(
        UUID id,
        String username,
        boolean enabled,
        List<String> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse from(User aggregate, List<String> roleNames) {
        return new UserResponse(
                aggregate.id().value(),
                aggregate.username(),
                aggregate.enabled(),
                roleNames,
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

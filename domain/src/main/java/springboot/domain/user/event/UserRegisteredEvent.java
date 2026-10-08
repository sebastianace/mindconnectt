package springboot.domain.user.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.common.event.DomainEvent;
import springboot.domain.user.model.valueobject.UserId;

public record UserRegisteredEvent(
        UserId id,
        String username,
        LocalDateTime occurredOn
) implements DomainEvent {
    public UserRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

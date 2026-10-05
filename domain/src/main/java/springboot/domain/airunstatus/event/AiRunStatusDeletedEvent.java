package springboot.domain.airunstatus.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.airunstatus.model.valueobject.AiRunStatusId;
import springboot.domain.common.event.DomainEvent;

public record AiRunStatusDeletedEvent(
        AiRunStatusId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public AiRunStatusDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

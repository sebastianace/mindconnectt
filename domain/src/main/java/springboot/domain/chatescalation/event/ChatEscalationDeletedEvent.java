package springboot.domain.chatescalation.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatescalation.model.valueobject.ChatEscalationId;
import springboot.domain.common.event.DomainEvent;

public record ChatEscalationDeletedEvent(
        ChatEscalationId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatEscalationDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

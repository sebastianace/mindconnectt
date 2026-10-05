package springboot.domain.chatescalationassignment.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import springboot.domain.common.event.DomainEvent;

public record ChatEscalationAssignmentDeletedEvent(
        ChatEscalationAssignmentId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatEscalationAssignmentDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

package springboot.domain.chatescalationstatushistory.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import springboot.domain.common.event.DomainEvent;

public record ChatEscalationStatusHistoryDeletedEvent(
        ChatEscalationStatusHistoryId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatEscalationStatusHistoryDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

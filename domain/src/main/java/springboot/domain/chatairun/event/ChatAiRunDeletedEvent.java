package springboot.domain.chatairun.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.common.event.DomainEvent;

public record ChatAiRunDeletedEvent(
        ChatAiRunId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatAiRunDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

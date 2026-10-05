package springboot.domain.chatairunerror.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import springboot.domain.common.event.DomainEvent;

public record ChatAiRunErrorRegisteredEvent(
        ChatAiRunErrorId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatAiRunErrorRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

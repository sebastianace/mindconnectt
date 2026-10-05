package springboot.domain.chatmessage.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatmessage.model.valueobject.ChatMessageId;
import springboot.domain.common.event.DomainEvent;

public record ChatMessageRegisteredEvent(
        ChatMessageId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatMessageRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

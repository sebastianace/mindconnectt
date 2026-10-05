package springboot.domain.chatconversation.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatconversation.model.valueobject.ChatConversationId;
import springboot.domain.common.event.DomainEvent;

public record ChatConversationDeletedEvent(
        ChatConversationId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatConversationDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

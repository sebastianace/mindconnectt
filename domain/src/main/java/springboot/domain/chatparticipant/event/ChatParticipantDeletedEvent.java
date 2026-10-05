package springboot.domain.chatparticipant.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatparticipant.model.valueobject.ChatParticipantId;
import springboot.domain.common.event.DomainEvent;

public record ChatParticipantDeletedEvent(
        ChatParticipantId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatParticipantDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

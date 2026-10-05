package springboot.domain.chatconversationaisetting.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatconversationaisetting.model.valueobject.ChatConversationAiSettingId;
import springboot.domain.common.event.DomainEvent;

public record ChatConversationAiSettingRegisteredEvent(
        ChatConversationAiSettingId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatConversationAiSettingRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}

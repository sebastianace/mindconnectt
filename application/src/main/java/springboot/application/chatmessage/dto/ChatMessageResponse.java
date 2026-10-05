package springboot.application.chatmessage.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatmessage.model.aggregate.ChatMessage;

public record ChatMessageResponse(
        UUID id,
        UUID conversationId,
        UUID messageTypeId,
        UUID participantId,
        String content,
        String metadata,
        LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage aggregate) {
        return new ChatMessageResponse(
                aggregate.id().value(),
                aggregate.conversationId().value(),
                aggregate.messageTypeId().value(),
                aggregate.participantId().value(),
                aggregate.content(),
                aggregate.metadata(),
                aggregate.createdAt());
    }
}

package springboot.application.chatconversation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatconversation.model.aggregate.ChatConversation;

public record ChatConversationResponse(
        UUID id,
        UUID conversationStatusId,
        UUID priorityId,
        LocalDateTime lastMessageAt,
        Boolean closed,
        LocalDateTime closedAt,
        UUID closedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ChatConversationResponse from(ChatConversation aggregate) {
        return new ChatConversationResponse(
                aggregate.id().value(),
                aggregate.conversationStatusId().value(),
                aggregate.priorityId().value(),
                aggregate.lastMessageAt(),
                aggregate.closed(),
                aggregate.closedAt(),
                aggregate.closedBy(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

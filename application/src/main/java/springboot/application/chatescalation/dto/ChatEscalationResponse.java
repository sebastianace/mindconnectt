package springboot.application.chatescalation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatescalation.model.aggregate.ChatEscalation;

public record ChatEscalationResponse(
        UUID id,
        UUID conversationId,
        UUID statusId,
        boolean fromAi,
        String reason,
        LocalDateTime createdAt
) {
    public static ChatEscalationResponse from(ChatEscalation aggregate) {
        return new ChatEscalationResponse(
                aggregate.id().value(),
                aggregate.conversationId().value(),
                aggregate.statusId().value(),
                aggregate.fromAi(),
                aggregate.reason(),
                aggregate.createdAt());
    }
}

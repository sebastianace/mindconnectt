package springboot.application.chatescalationstatushistory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;

public record ChatEscalationStatusHistoryResponse(
        UUID id,
        UUID escalationId,
        UUID escalationStatusId,
        LocalDateTime changedAt,
        LocalDateTime createdAt
) {
    public static ChatEscalationStatusHistoryResponse from(ChatEscalationStatusHistory aggregate) {
        return new ChatEscalationStatusHistoryResponse(
                aggregate.id().value(),
                aggregate.escalationId().value(),
                aggregate.escalationStatusId().value(),
                aggregate.changedAt(),
                aggregate.createdAt());
    }
}

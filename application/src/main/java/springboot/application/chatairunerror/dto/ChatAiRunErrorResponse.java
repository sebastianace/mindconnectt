package springboot.application.chatairunerror.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatairunerror.model.aggregate.ChatAiRunError;

public record ChatAiRunErrorResponse(
        UUID id,
        UUID aiRunId,
        String errorMessage,
        String errorCode,
        String providerErrorId,
        LocalDateTime createdAt
) {
    public static ChatAiRunErrorResponse from(ChatAiRunError aggregate) {
        return new ChatAiRunErrorResponse(
                aggregate.id().value(),
                aggregate.aiRunId().value(),
                aggregate.errorMessage(),
                aggregate.errorCode(),
                aggregate.providerErrorId(),
                aggregate.createdAt());
    }
}

package springboot.application.chatairun.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatairun.model.aggregate.ChatAiRun;

public record ChatAiRunResponse(
        UUID id,
        UUID conversationId,
        UUID messageId,
        UUID modelId,
        UUID aiRunStatusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ChatAiRunResponse from(ChatAiRun aggregate) {
        return new ChatAiRunResponse(
                aggregate.id().value(),
                aggregate.conversationId().value(),
                aggregate.messageId().value(),
                aggregate.modelId().value(),
                aggregate.aiRunStatusId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

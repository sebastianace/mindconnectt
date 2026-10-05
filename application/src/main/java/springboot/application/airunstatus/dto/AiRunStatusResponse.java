package springboot.application.airunstatus.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.airunstatus.model.aggregate.AiRunStatus;

public record AiRunStatusResponse(
        UUID id,
        String nameStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AiRunStatusResponse from(AiRunStatus aggregate) {
        return new AiRunStatusResponse(
                aggregate.id().value(),
                aggregate.nameStatus(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

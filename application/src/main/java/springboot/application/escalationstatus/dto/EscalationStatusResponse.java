package springboot.application.escalationstatus.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;

public record EscalationStatusResponse(
        UUID id,
        String nameStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EscalationStatusResponse from(EscalationStatus aggregate) {
        return new EscalationStatusResponse(
                aggregate.id().value(),
                aggregate.nameStatus(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

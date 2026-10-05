package springboot.application.sendertype.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.sendertype.model.aggregate.SenderType;

public record SenderTypeResponse(
        UUID id,
        String nameType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SenderTypeResponse from(SenderType aggregate) {
        return new SenderTypeResponse(
                aggregate.id().value(),
                aggregate.nameType(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

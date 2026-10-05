package springboot.application.consenttype.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.consenttype.model.aggregate.ConsentType;

public record ConsentTypeResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ConsentTypeResponse from(ConsentType aggregate) {
        return new ConsentTypeResponse(
                aggregate.id().value(),
                aggregate.code(),
                aggregate.name(),
                aggregate.active(),
                aggregate.description(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

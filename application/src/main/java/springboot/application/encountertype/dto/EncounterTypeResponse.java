package springboot.application.encountertype.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.encountertype.model.aggregate.EncounterType;

public record EncounterTypeResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EncounterTypeResponse from(EncounterType aggregate) {
        return new EncounterTypeResponse(
                aggregate.id().value(),
                aggregate.code(),
                aggregate.name(),
                aggregate.active(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

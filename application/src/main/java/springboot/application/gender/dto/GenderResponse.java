package springboot.application.gender.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.gender.model.aggregate.Gender;

public record GenderResponse(
        UUID id,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GenderResponse from(Gender aggregate) {
        return new GenderResponse(
                aggregate.id().value(),
                aggregate.description(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

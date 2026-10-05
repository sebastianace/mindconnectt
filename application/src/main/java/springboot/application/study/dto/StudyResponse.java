package springboot.application.study.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.study.model.aggregate.Study;

public record StudyResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StudyResponse from(Study aggregate) {
        return new StudyResponse(
                aggregate.id().value(),
                aggregate.name(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

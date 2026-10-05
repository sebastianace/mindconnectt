package springboot.application.assessmenttype.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.assessmenttype.model.aggregate.AssessmentType;

public record AssessmentTypeResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AssessmentTypeResponse from(AssessmentType aggregate) {
        return new AssessmentTypeResponse(
                aggregate.id().value(),
                aggregate.code(),
                aggregate.name(),
                aggregate.active(),
                aggregate.description(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

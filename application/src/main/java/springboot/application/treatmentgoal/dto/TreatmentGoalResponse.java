package springboot.application.treatmentgoal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.treatmentgoal.model.aggregate.TreatmentGoal;

public record TreatmentGoalResponse(
        UUID id,
        UUID treatmentPlanId,
        String description,
        LocalDate targetDate,
        LocalDateTime completedAt,
        String notes,
        UUID treatmentGoalStatusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TreatmentGoalResponse from(TreatmentGoal aggregate) {
        return new TreatmentGoalResponse(
                aggregate.id().value(),
                aggregate.treatmentPlanId().value(),
                aggregate.description(),
                aggregate.targetDate(),
                aggregate.completedAt(),
                aggregate.notes(),
                aggregate.treatmentGoalStatusId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

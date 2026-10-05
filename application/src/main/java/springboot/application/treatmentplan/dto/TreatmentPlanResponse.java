package springboot.application.treatmentplan.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.treatmentplan.model.aggregate.TreatmentPlan;

public record TreatmentPlanResponse(
        UUID id,
        UUID encounterId,
        UUID professionalId,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        UUID treatmentStatusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TreatmentPlanResponse from(TreatmentPlan aggregate) {
        return new TreatmentPlanResponse(
                aggregate.id().value(),
                aggregate.encounterId().value(),
                aggregate.professionalId().value(),
                aggregate.title(),
                aggregate.description(),
                aggregate.startDate(),
                aggregate.endDate(),
                aggregate.treatmentStatusId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

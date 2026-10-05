package springboot.infrastructure.treatmentgoal.adapters.in.rest.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTreatmentGoalRequest(
        @NotNull(message = "treatmentPlanId is required")
        UUID treatmentPlanId,

        @NotBlank(message = "description is required")
        String description,

        @NotNull(message = "targetDate is required")
        LocalDate targetDate,

        @NotNull(message = "completedAt is required")
        LocalDateTime completedAt,

        @NotBlank(message = "notes is required")
        String notes,

        @NotNull(message = "treatmentGoalStatusId is required")
        UUID treatmentGoalStatusId
) {
}

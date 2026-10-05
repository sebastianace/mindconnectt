package springboot.infrastructure.encounter.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEncounterRequest(
        @NotNull(message = "clinicalRecordId is required")
        UUID clinicalRecordId,

        @NotNull(message = "professionalId is required")
        UUID professionalId,

        @NotNull(message = "encounterTypeId is required")
        UUID encounterTypeId,

        @NotNull(message = "startedAt is required")
        LocalDateTime startedAt,

        @NotNull(message = "endedAt is required")
        LocalDateTime endedAt,

        @NotBlank(message = "reasonForVisit is required")
        String reasonForVisit,

        @NotBlank(message = "currentCondition is required")
        String currentCondition,

        @NotNull(message = "modalityId is required")
        UUID modalityId,

        @NotNull(message = "statusId is required")
        UUID statusId,

        @NotNull(message = "createdBy is required")
        UUID createdBy,

        @NotNull(message = "updatedBy is required")
        UUID updatedBy
) {
}

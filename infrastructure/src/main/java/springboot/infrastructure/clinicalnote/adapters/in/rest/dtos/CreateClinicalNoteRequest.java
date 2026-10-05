package springboot.infrastructure.clinicalnote.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateClinicalNoteRequest(
        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotNull(message = "professionalId is required")
        UUID professionalId,

        @NotBlank(message = "subjective is required")
        String subjective,

        @NotBlank(message = "objective is required")
        String objective,

        @NotBlank(message = "assessment is required")
        String assessment,

        @NotBlank(message = "plan is required")
        String plan,

        @NotBlank(message = "additionalNotes is required")
        String additionalNotes,

        @NotNull(message = "signedAt is required")
        LocalDateTime signedAt
) {
}

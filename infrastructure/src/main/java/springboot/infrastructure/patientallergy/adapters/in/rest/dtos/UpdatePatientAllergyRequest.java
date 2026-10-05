package springboot.infrastructure.patientallergy.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePatientAllergyRequest(
        @NotNull(message = "patientId is required")
        UUID patientId,

        @NotBlank(message = "substance is required")
        @Size(max = 200, message = "substance must have at most 200 characters")
        String substance,

        String reaction,

        @NotBlank(message = "severity is required")
        @Size(max = 20, message = "severity must have at most 20 characters")
        String severity,

        @NotNull(message = "active is required")
        Boolean active,

        @NotNull(message = "recordedAt is required")
        LocalDateTime recordedAt,

        @NotNull(message = "recordedBy is required")
        UUID recordedBy
) {
}

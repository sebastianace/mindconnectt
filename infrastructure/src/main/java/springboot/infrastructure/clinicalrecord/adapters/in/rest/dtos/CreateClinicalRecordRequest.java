package springboot.infrastructure.clinicalrecord.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClinicalRecordRequest(
        @NotNull(message = "patientId is required")
        UUID patientId,

        @NotNull(message = "creationDate is required")
        LocalDateTime creationDate,

        @NotBlank(message = "recordNumber is required")
        @Size(max = 50, message = "recordNumber must have at most 50 characters")
        String recordNumber,

        @NotNull(message = "openedAt is required")
        LocalDateTime openedAt,

        @NotNull(message = "closedAt is required")
        LocalDateTime closedAt,

        @NotNull(message = "statusId is required")
        UUID statusId,

        @NotNull(message = "createdBy is required")
        UUID createdBy
) {
}

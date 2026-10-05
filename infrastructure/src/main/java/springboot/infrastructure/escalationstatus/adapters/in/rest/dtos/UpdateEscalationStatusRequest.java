package springboot.infrastructure.escalationstatus.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateEscalationStatusRequest(
        @NotBlank(message = "nameStatus is required")
        @Size(max = 50, message = "nameStatus must have at most 50 characters")
        String nameStatus
) {
}

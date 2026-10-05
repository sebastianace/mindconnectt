package springboot.infrastructure.priority.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePriorityRequest(
        @NotBlank(message = "namePriority is required")
        @Size(max = 50, message = "namePriority must have at most 50 characters")
        String namePriority
) {
}

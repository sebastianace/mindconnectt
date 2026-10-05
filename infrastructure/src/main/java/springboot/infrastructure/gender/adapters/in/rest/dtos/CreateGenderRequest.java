package springboot.infrastructure.gender.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGenderRequest(
        @NotBlank(message = "description is required")
        @Size(max = 50, message = "description must have at most 50 characters")
        String description
) {
}

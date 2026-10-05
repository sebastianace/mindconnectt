package springboot.infrastructure.contact.adapters.in.rest.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateContactRequest(
        @NotBlank(message = "fullName is required")
        @Size(max = 200, message = "fullName must have at most 200 characters")
        String fullName,

        @NotBlank(message = "email is required")
        @Size(max = 150, message = "email must have at most 150 characters")
        @Email(message = "email must be a valid email")
        String email,

        @NotBlank(message = "notes is required")
        String notes,

        @NotNull(message = "cityId is required")
        UUID cityId,

        @NotNull(message = "createdBy is required")
        UUID createdBy,

        UUID updatedBy
) {
}

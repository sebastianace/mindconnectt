package springboot.infrastructure.diagnosticsystem.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDiagnosticSystemRequest(
        @NotBlank(message = "code is required")
        @Size(max = 20, message = "code must have at most 20 characters")
        String code,

        @NotBlank(message = "name is required")
        @Size(max = 50, message = "name must have at most 50 characters")
        String name,

        @NotNull(message = "active is required")
        Boolean active,

        @NotBlank(message = "version is required")
        @Size(max = 20, message = "version must have at most 20 characters")
        String version
) {
}

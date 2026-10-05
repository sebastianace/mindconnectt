package springboot.infrastructure.patient.adapters.in.rest.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record CreatePatientRequest(
        @NotNull(message = "documentTypeId is required")
        UUID documentTypeId,

        @NotBlank(message = "documentNumber is required")
        @Size(max = 30, message = "documentNumber must have at most 30 characters")
        String documentNumber,

        @NotBlank(message = "firstName is required")
        @Size(max = 50, message = "firstName must have at most 50 characters")
        String firstName,

        @Size(max = 50, message = "middleName must have at most 50 characters")
        String middleName,

        @NotBlank(message = "lastName is required")
        @Size(max = 50, message = "lastName must have at most 50 characters")
        String lastName,

        @Size(max = 50, message = "secondLastName must have at most 50 characters")
        String secondLastName,

        @NotNull(message = "birthDate is required")
        @PastOrPresent(message = "birthDate cannot be in the future")
        LocalDate birthDate,

        @NotNull(message = "biologicalSexId is required")
        UUID biologicalSexId,

        @NotNull(message = "genderIdentityId is required")
        UUID genderIdentityId,

        @NotBlank(message = "email is required")
        @Size(max = 150, message = "email must have at most 150 characters")
        @Email(message = "email must be a valid email")
        String email,

        @NotBlank(message = "phone is required")
        @Size(max = 30, message = "phone must have at most 30 characters")
        String phone,

        @NotBlank(message = "address is required")
        @Size(max = 250, message = "address must have at most 250 characters")
        String address,

        @NotNull(message = "active is required")
        Boolean active,

        UUID createdBy,

        UUID updatedBy,

        @NotNull(message = "cityId is required")
        UUID cityId
) {
}

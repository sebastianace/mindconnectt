package springboot.application.patient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.patient.model.aggregate.Patient;

public record PatientResponse(
        UUID id,
        UUID documentTypeId,
        String documentNumber,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        LocalDate birthDate,
        UUID biologicalSexId,
        UUID genderIdentityId,
        String email,
        String phone,
        String address,
        boolean active,
        UUID createdBy,
        UUID updatedBy,
        UUID cityId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PatientResponse from(Patient aggregate) {
        return new PatientResponse(
                aggregate.id().value(),
                aggregate.documentTypeId().value(),
                aggregate.documentNumber(),
                aggregate.firstName(),
                aggregate.middleName(),
                aggregate.lastName(),
                aggregate.secondLastName(),
                aggregate.birthDate(),
                aggregate.biologicalSexId().value(),
                aggregate.genderIdentityId().value(),
                aggregate.email(),
                aggregate.phone(),
                aggregate.address(),
                aggregate.active(),
                aggregate.createdBy() == null ? null : aggregate.createdBy().value(),
                aggregate.updatedBy() == null ? null : aggregate.updatedBy().value(),
                aggregate.cityId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

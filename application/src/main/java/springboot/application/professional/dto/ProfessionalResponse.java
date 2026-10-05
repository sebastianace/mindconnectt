package springboot.application.professional.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.professional.model.aggregate.Professional;

public record ProfessionalResponse(
        UUID id,
        UUID documentTypeId,
        String documentNumber,
        String firstName,
        String lastName,
        UUID professionalTypeId,
        String licenseNumber,
        boolean active,
        UUID cityId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProfessionalResponse from(Professional aggregate) {
        return new ProfessionalResponse(
                aggregate.id().value(),
                aggregate.documentTypeId().value(),
                aggregate.documentNumber(),
                aggregate.firstName(),
                aggregate.lastName(),
                aggregate.professionalTypeId().value(),
                aggregate.licenseNumber(),
                aggregate.active(),
                aggregate.cityId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

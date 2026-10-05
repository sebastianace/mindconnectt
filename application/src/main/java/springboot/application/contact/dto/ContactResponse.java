package springboot.application.contact.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.contact.model.aggregate.Contact;

public record ContactResponse(
        UUID id,
        String fullName,
        String email,
        String notes,
        UUID cityId,
        UUID createdBy,
        UUID updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ContactResponse from(Contact aggregate) {
        return new ContactResponse(
                aggregate.id().value(),
                aggregate.fullName(),
                aggregate.email(),
                aggregate.notes(),
                aggregate.cityId().value(),
                aggregate.createdBy().value(),
                aggregate.updatedBy() == null ? null : aggregate.updatedBy().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

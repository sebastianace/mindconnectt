package springboot.application.emailcontact.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.emailcontact.model.aggregate.EmailContact;

public record EmailContactResponse(
        UUID id,
        UUID contactId,
        String email,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EmailContactResponse from(EmailContact aggregate) {
        return new EmailContactResponse(
                aggregate.id().value(),
                aggregate.contactId().value(),
                aggregate.email(),
                aggregate.notes(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}

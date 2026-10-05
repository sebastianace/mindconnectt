package springboot.application.phonecontact.dto;

import java.util.UUID;

import springboot.domain.phonecontact.model.aggregate.PhoneContact;

public record PhoneContactResponse(
        UUID id,
        UUID contactId,
        String phone,
        String notes
) {
    public static PhoneContactResponse from(PhoneContact aggregate) {
        return new PhoneContactResponse(
                aggregate.id().value(),
                aggregate.contactId().value(),
                aggregate.phone(),
                aggregate.notes());
    }
}

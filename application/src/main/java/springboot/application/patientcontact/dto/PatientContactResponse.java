package springboot.application.patientcontact.dto;

import java.util.UUID;

import springboot.domain.patientcontact.model.aggregate.PatientContact;

public record PatientContactResponse(
        UUID id,
        UUID contactId,
        UUID patientId,
        boolean primaryContact,
        boolean emergencyContact,
        UUID relationshipTypeId
) {
    public static PatientContactResponse from(PatientContact aggregate) {
        return new PatientContactResponse(
                aggregate.id().value(),
                aggregate.contactId().value(),
                aggregate.patientId().value(),
                aggregate.primaryContact(),
                aggregate.emergencyContact(),
                aggregate.relationshipTypeId().value());
    }
}

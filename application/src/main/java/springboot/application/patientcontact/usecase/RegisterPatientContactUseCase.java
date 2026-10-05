package springboot.application.patientcontact.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.patientcontact.command.RegisterPatientContactCommand;
import springboot.application.patientcontact.dto.PatientContactResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.patientcontact.model.aggregate.PatientContact;
import springboot.domain.patientcontact.port.repository.PatientContactRepository;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;

public class RegisterPatientContactUseCase {
    private final PatientContactRepository repository;
    private final ContactRepository contactRepository;
    private final PatientRepository patientRepository;
    private final RelationshipTypeRepository relationshipTypeRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterPatientContactUseCase(
            PatientContactRepository repository,
            ContactRepository contactRepository,
            PatientRepository patientRepository,
            RelationshipTypeRepository relationshipTypeRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.patientRepository = patientRepository;
        this.relationshipTypeRepository = relationshipTypeRepository;
        this.eventPublisher = eventPublisher;
    }

    public PatientContactResponse execute(RegisterPatientContactCommand command) {
        validateReferences(command);
        PatientContact aggregate = PatientContact.register(
                command.contactId(),
                command.patientId(),
                command.primaryContact(),
                command.emergencyContact(),
                command.relationshipTypeId());
        PatientContact saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PatientContactResponse.from(saved);
    }

    private void validateReferences(RegisterPatientContactCommand command) {
        if (!contactRepository.existsById(command.contactId())) {
            throw new ReferenceNotFoundApplicationException("Contact", command.contactId().value());
        }
        if (!patientRepository.existsById(command.patientId())) {
            throw new ReferenceNotFoundApplicationException("Patient", command.patientId().value());
        }
        if (!relationshipTypeRepository.existsById(command.relationshipTypeId())) {
            throw new ReferenceNotFoundApplicationException("RelationshipType", command.relationshipTypeId().value());
        }
    }
}

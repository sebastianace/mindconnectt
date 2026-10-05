package springboot.application.phonecontact.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.phonecontact.command.UpdatePhoneContactCommand;
import springboot.application.phonecontact.dto.PhoneContactResponse;
import springboot.application.phonecontact.exception.PhoneContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.phonecontact.model.aggregate.PhoneContact;
import springboot.domain.phonecontact.port.repository.PhoneContactRepository;

public class UpdatePhoneContactUseCase {
    private final PhoneContactRepository repository;
    private final ContactRepository contactRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdatePhoneContactUseCase(
            PhoneContactRepository repository,
            ContactRepository contactRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.eventPublisher = eventPublisher;
    }

    public PhoneContactResponse execute(UpdatePhoneContactCommand command) {
        PhoneContact aggregate = repository.findById(command.id())
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.contactId(),
                command.phone(),
                command.notes());
        PhoneContact saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PhoneContactResponse.from(saved);
    }

    private void validateReferences(UpdatePhoneContactCommand command) {
        if (!contactRepository.existsById(command.contactId())) {
            throw new ReferenceNotFoundApplicationException("Contact", command.contactId().value());
        }
    }
}

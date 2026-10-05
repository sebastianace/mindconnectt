package springboot.application.emailcontact.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.emailcontact.command.UpdateEmailContactCommand;
import springboot.application.emailcontact.dto.EmailContactResponse;
import springboot.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.emailcontact.model.aggregate.EmailContact;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;

public class UpdateEmailContactUseCase {
    private final EmailContactRepository repository;
    private final ContactRepository contactRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateEmailContactUseCase(
            EmailContactRepository repository,
            ContactRepository contactRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.eventPublisher = eventPublisher;
    }

    public EmailContactResponse execute(UpdateEmailContactCommand command) {
        EmailContact aggregate = repository.findById(command.id())
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.contactId(),
                command.email(),
                command.notes());
        EmailContact saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EmailContactResponse.from(saved);
    }

    private void validateReferences(UpdateEmailContactCommand command) {
        if (!contactRepository.existsById(command.contactId())) {
            throw new ReferenceNotFoundApplicationException("Contact", command.contactId().value());
        }
    }
}

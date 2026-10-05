package springboot.application.contact.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.contact.exception.ContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.event.ContactDeletedEvent;
import springboot.domain.contact.model.aggregate.Contact;
import springboot.domain.contact.model.valueobject.ContactId;
import springboot.domain.contact.port.repository.ContactRepository;

public class DeleteContactUseCase {
    private final ContactRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteContactUseCase(ContactRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ContactDeletedEvent execute(ContactId id) {
        Contact aggregate = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ContactDeletedEvent event = new ContactDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

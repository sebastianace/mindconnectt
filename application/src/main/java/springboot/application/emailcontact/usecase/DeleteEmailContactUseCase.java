package springboot.application.emailcontact.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.emailcontact.event.EmailContactDeletedEvent;
import springboot.domain.emailcontact.model.aggregate.EmailContact;
import springboot.domain.emailcontact.model.valueobject.EmailContactId;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;

public class DeleteEmailContactUseCase {
    private final EmailContactRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEmailContactUseCase(EmailContactRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EmailContactDeletedEvent execute(EmailContactId id) {
        EmailContact aggregate = repository.findById(id)
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EmailContactDeletedEvent event = new EmailContactDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

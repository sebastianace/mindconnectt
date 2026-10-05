package springboot.application.phonecontact.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.phonecontact.exception.PhoneContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.phonecontact.event.PhoneContactDeletedEvent;
import springboot.domain.phonecontact.model.aggregate.PhoneContact;
import springboot.domain.phonecontact.model.valueobject.PhoneContactId;
import springboot.domain.phonecontact.port.repository.PhoneContactRepository;

public class DeletePhoneContactUseCase {
    private final PhoneContactRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeletePhoneContactUseCase(PhoneContactRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PhoneContactDeletedEvent execute(PhoneContactId id) {
        PhoneContact aggregate = repository.findById(id)
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        PhoneContactDeletedEvent event = new PhoneContactDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

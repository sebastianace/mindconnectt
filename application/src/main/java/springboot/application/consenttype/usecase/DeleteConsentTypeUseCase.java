package springboot.application.consenttype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.consenttype.event.ConsentTypeDeletedEvent;
import springboot.domain.consenttype.model.aggregate.ConsentType;
import springboot.domain.consenttype.model.valueobject.ConsentTypeId;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;

public class DeleteConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteConsentTypeUseCase(ConsentTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConsentTypeDeletedEvent execute(ConsentTypeId id) {
        ConsentType aggregate = repository.findById(id)
                .orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ConsentTypeDeletedEvent event = new ConsentTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

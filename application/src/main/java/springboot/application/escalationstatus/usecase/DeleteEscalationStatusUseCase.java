package springboot.application.escalationstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.event.EscalationStatusDeletedEvent;
import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;
import springboot.domain.escalationstatus.model.valueobject.EscalationStatusId;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class DeleteEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEscalationStatusUseCase(EscalationStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EscalationStatusDeletedEvent execute(EscalationStatusId id) {
        EscalationStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new EscalationStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EscalationStatusDeletedEvent event = new EscalationStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

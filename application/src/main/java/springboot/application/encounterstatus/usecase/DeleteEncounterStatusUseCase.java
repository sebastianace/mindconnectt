package springboot.application.encounterstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounterstatus.event.EncounterStatusDeletedEvent;
import springboot.domain.encounterstatus.model.aggregate.EncounterStatus;
import springboot.domain.encounterstatus.model.valueobject.EncounterStatusId;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;

public class DeleteEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEncounterStatusUseCase(EncounterStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterStatusDeletedEvent execute(EncounterStatusId id) {
        EncounterStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EncounterStatusDeletedEvent event = new EncounterStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

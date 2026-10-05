package springboot.application.encounter.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.encounter.exception.EncounterNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.event.EncounterDeletedEvent;
import springboot.domain.encounter.model.aggregate.Encounter;
import springboot.domain.encounter.model.valueobject.EncounterId;
import springboot.domain.encounter.port.repository.EncounterRepository;

public class DeleteEncounterUseCase {
    private final EncounterRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEncounterUseCase(EncounterRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterDeletedEvent execute(EncounterId id) {
        Encounter aggregate = repository.findById(id)
                .orElseThrow(() -> new EncounterNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EncounterDeletedEvent event = new EncounterDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

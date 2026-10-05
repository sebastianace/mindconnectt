package springboot.application.encountermodality.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountermodality.event.EncounterModalityDeletedEvent;
import springboot.domain.encountermodality.model.aggregate.EncounterModality;
import springboot.domain.encountermodality.model.valueobject.EncounterModalityId;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;

public class DeleteEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEncounterModalityUseCase(EncounterModalityRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterModalityDeletedEvent execute(EncounterModalityId id) {
        EncounterModality aggregate = repository.findById(id)
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EncounterModalityDeletedEvent event = new EncounterModalityDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

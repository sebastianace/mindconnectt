package springboot.application.encountertype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountertype.event.EncounterTypeDeletedEvent;
import springboot.domain.encountertype.model.aggregate.EncounterType;
import springboot.domain.encountertype.model.valueobject.EncounterTypeId;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;

public class DeleteEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteEncounterTypeUseCase(EncounterTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterTypeDeletedEvent execute(EncounterTypeId id) {
        EncounterType aggregate = repository.findById(id)
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        EncounterTypeDeletedEvent event = new EncounterTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

package springboot.application.stateregion.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.stateregion.exception.StateRegionNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.stateregion.event.StateRegionDeletedEvent;
import springboot.domain.stateregion.model.aggregate.StateRegion;
import springboot.domain.stateregion.model.valueobject.StateRegionId;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class DeleteStateRegionUseCase {
    private final StateRegionRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteStateRegionUseCase(StateRegionRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public StateRegionDeletedEvent execute(StateRegionId id) {
        StateRegion aggregate = repository.findById(id)
                .orElseThrow(() -> new StateRegionNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        StateRegionDeletedEvent event = new StateRegionDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

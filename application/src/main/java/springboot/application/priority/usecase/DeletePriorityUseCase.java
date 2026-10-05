package springboot.application.priority.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.priority.exception.PriorityNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.priority.event.PriorityDeletedEvent;
import springboot.domain.priority.model.aggregate.Priority;
import springboot.domain.priority.model.valueobject.PriorityId;
import springboot.domain.priority.port.repository.PriorityRepository;

public class DeletePriorityUseCase {
    private final PriorityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeletePriorityUseCase(PriorityRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PriorityDeletedEvent execute(PriorityId id) {
        Priority aggregate = repository.findById(id)
                .orElseThrow(() -> new PriorityNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        PriorityDeletedEvent event = new PriorityDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

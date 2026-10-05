package springboot.application.priority.usecase;

import springboot.application.priority.command.UpdatePriorityCommand;
import springboot.application.priority.dto.PriorityResponse;
import springboot.application.priority.exception.PriorityNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.priority.model.aggregate.Priority;
import springboot.domain.priority.port.repository.PriorityRepository;

public class UpdatePriorityUseCase {
    private final PriorityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdatePriorityUseCase(
            PriorityRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PriorityResponse execute(UpdatePriorityCommand command) {
        Priority aggregate = repository.findById(command.id())
                .orElseThrow(() -> new PriorityNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.namePriority());
        Priority saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PriorityResponse.from(saved);
    }
}

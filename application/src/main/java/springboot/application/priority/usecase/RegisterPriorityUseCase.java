package springboot.application.priority.usecase;

import springboot.application.priority.command.RegisterPriorityCommand;
import springboot.application.priority.dto.PriorityResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.priority.model.aggregate.Priority;
import springboot.domain.priority.port.repository.PriorityRepository;

public class RegisterPriorityUseCase {
    private final PriorityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterPriorityUseCase(
            PriorityRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PriorityResponse execute(RegisterPriorityCommand command) {
        Priority aggregate = Priority.register(
                command.namePriority());
        Priority saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PriorityResponse.from(saved);
    }
}

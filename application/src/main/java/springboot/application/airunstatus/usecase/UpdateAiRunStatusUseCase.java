package springboot.application.airunstatus.usecase;

import springboot.application.airunstatus.command.UpdateAiRunStatusCommand;
import springboot.application.airunstatus.dto.AiRunStatusResponse;
import springboot.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import springboot.domain.airunstatus.model.aggregate.AiRunStatus;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class UpdateAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateAiRunStatusUseCase(
            AiRunStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AiRunStatusResponse execute(UpdateAiRunStatusCommand command) {
        AiRunStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new AiRunStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameStatus());
        AiRunStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return AiRunStatusResponse.from(saved);
    }
}

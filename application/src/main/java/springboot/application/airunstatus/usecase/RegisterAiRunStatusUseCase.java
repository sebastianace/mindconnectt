package springboot.application.airunstatus.usecase;

import springboot.application.airunstatus.command.RegisterAiRunStatusCommand;
import springboot.application.airunstatus.dto.AiRunStatusResponse;
import springboot.domain.airunstatus.model.aggregate.AiRunStatus;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterAiRunStatusUseCase(
            AiRunStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AiRunStatusResponse execute(RegisterAiRunStatusCommand command) {
        AiRunStatus aggregate = AiRunStatus.register(
                command.nameStatus());
        AiRunStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return AiRunStatusResponse.from(saved);
    }
}

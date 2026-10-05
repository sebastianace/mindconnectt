package springboot.application.escalationstatus.usecase;

import springboot.application.escalationstatus.command.RegisterEscalationStatusCommand;
import springboot.application.escalationstatus.dto.EscalationStatusResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class RegisterEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterEscalationStatusUseCase(
            EscalationStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EscalationStatusResponse execute(RegisterEscalationStatusCommand command) {
        EscalationStatus aggregate = EscalationStatus.register(
                command.nameStatus());
        EscalationStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EscalationStatusResponse.from(saved);
    }
}

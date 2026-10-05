package springboot.application.escalationstatus.usecase;

import springboot.application.escalationstatus.command.UpdateEscalationStatusCommand;
import springboot.application.escalationstatus.dto.EscalationStatusResponse;
import springboot.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class UpdateEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateEscalationStatusUseCase(
            EscalationStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EscalationStatusResponse execute(UpdateEscalationStatusCommand command) {
        EscalationStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new EscalationStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameStatus());
        EscalationStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EscalationStatusResponse.from(saved);
    }
}

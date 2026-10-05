package springboot.application.encounterstatus.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.encounterstatus.command.RegisterEncounterStatusCommand;
import springboot.application.encounterstatus.dto.EncounterStatusResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounterstatus.model.aggregate.EncounterStatus;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;

public class RegisterEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterEncounterStatusUseCase(
            EncounterStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterStatusResponse execute(RegisterEncounterStatusCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("EncounterStatus", "code", command.code());
        }
        EncounterStatus aggregate = EncounterStatus.register(
                command.code(),
                command.name(),
                command.active());
        EncounterStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterStatusResponse.from(saved);
    }
}

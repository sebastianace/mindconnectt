package springboot.application.encounterstatus.usecase;

import springboot.application.encounterstatus.command.UpdateEncounterStatusCommand;
import springboot.application.encounterstatus.dto.EncounterStatusResponse;
import springboot.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounterstatus.model.aggregate.EncounterStatus;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;

public class UpdateEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateEncounterStatusUseCase(
            EncounterStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterStatusResponse execute(UpdateEncounterStatusCommand command) {
        EncounterStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new EncounterStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        EncounterStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterStatusResponse.from(saved);
    }
}

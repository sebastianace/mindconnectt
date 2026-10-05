package springboot.application.encountertype.usecase;

import springboot.application.encountertype.command.UpdateEncounterTypeCommand;
import springboot.application.encountertype.dto.EncounterTypeResponse;
import springboot.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountertype.model.aggregate.EncounterType;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;

public class UpdateEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateEncounterTypeUseCase(
            EncounterTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterTypeResponse execute(UpdateEncounterTypeCommand command) {
        EncounterType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        EncounterType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterTypeResponse.from(saved);
    }
}

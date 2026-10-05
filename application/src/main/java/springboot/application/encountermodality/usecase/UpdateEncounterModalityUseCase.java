package springboot.application.encountermodality.usecase;

import springboot.application.encountermodality.command.UpdateEncounterModalityCommand;
import springboot.application.encountermodality.dto.EncounterModalityResponse;
import springboot.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountermodality.model.aggregate.EncounterModality;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;

public class UpdateEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateEncounterModalityUseCase(
            EncounterModalityRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterModalityResponse execute(UpdateEncounterModalityCommand command) {
        EncounterModality aggregate = repository.findById(command.id())
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        EncounterModality saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterModalityResponse.from(saved);
    }
}

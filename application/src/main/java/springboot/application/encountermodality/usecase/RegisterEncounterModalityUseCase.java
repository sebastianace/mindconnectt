package springboot.application.encountermodality.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.encountermodality.command.RegisterEncounterModalityCommand;
import springboot.application.encountermodality.dto.EncounterModalityResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountermodality.model.aggregate.EncounterModality;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;

public class RegisterEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterEncounterModalityUseCase(
            EncounterModalityRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterModalityResponse execute(RegisterEncounterModalityCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("EncounterModality", "code", command.code());
        }
        EncounterModality aggregate = EncounterModality.register(
                command.code(),
                command.name(),
                command.active());
        EncounterModality saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterModalityResponse.from(saved);
    }
}

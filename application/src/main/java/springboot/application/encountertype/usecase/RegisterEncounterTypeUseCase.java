package springboot.application.encountertype.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.encountertype.command.RegisterEncounterTypeCommand;
import springboot.application.encountertype.dto.EncounterTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountertype.model.aggregate.EncounterType;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;

public class RegisterEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterEncounterTypeUseCase(
            EncounterTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterTypeResponse execute(RegisterEncounterTypeCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("EncounterType", "code", command.code());
        }
        EncounterType aggregate = EncounterType.register(
                command.code(),
                command.name(),
                command.active());
        EncounterType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterTypeResponse.from(saved);
    }
}

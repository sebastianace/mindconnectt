package springboot.application.stateregion.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.stateregion.command.UpdateStateRegionCommand;
import springboot.application.stateregion.dto.StateRegionResponse;
import springboot.application.stateregion.exception.StateRegionNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.stateregion.model.aggregate.StateRegion;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class UpdateStateRegionUseCase {
    private final StateRegionRepository repository;
    private final CountryRepository countryRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateStateRegionUseCase(
            StateRegionRepository repository,
            CountryRepository countryRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.eventPublisher = eventPublisher;
    }

    public StateRegionResponse execute(UpdateStateRegionCommand command) {
        StateRegion aggregate = repository.findById(command.id())
                .orElseThrow(() -> new StateRegionNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.nameRegion(),
                command.codeRegion(),
                command.description(),
                command.active(),
                command.countryId());
        StateRegion saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return StateRegionResponse.from(saved);
    }

    private void validateReferences(UpdateStateRegionCommand command) {
        if (!countryRepository.existsById(command.countryId())) {
            throw new ReferenceNotFoundApplicationException("Country", command.countryId().value());
        }
    }
}

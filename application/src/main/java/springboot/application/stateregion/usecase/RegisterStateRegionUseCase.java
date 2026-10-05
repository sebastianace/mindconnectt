package springboot.application.stateregion.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.stateregion.command.RegisterStateRegionCommand;
import springboot.application.stateregion.dto.StateRegionResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.stateregion.model.aggregate.StateRegion;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class RegisterStateRegionUseCase {
    private final StateRegionRepository repository;
    private final CountryRepository countryRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterStateRegionUseCase(
            StateRegionRepository repository,
            CountryRepository countryRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.eventPublisher = eventPublisher;
    }

    public StateRegionResponse execute(RegisterStateRegionCommand command) {
        validateReferences(command);
        if (repository.existsByCountryIdAndCode(command.countryId(), command.codeRegion())) {
            throw new DuplicateResourceApplicationException("StateRegion", "codeRegion", command.codeRegion());
        }
        StateRegion aggregate = StateRegion.register(
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

    private void validateReferences(RegisterStateRegionCommand command) {
        if (!countryRepository.existsById(command.countryId())) {
            throw new ReferenceNotFoundApplicationException("Country", command.countryId().value());
        }
    }
}

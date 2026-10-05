package springboot.application.citymunicipality.usecase;

import springboot.application.citymunicipality.command.UpdateCityMunicipalityCommand;
import springboot.application.citymunicipality.dto.CityMunicipalityResponse;
import springboot.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class UpdateCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;
    private final StateRegionRepository stateRegionRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateCityMunicipalityUseCase(
            CityMunicipalityRepository repository,
            StateRegionRepository stateRegionRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.stateRegionRepository = stateRegionRepository;
        this.eventPublisher = eventPublisher;
    }

    public CityMunicipalityResponse execute(UpdateCityMunicipalityCommand command) {
        CityMunicipality aggregate = repository.findById(command.id())
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.nameCity(),
                command.codeCity(),
                command.description(),
                command.active(),
                command.regionId());
        CityMunicipality saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return CityMunicipalityResponse.from(saved);
    }

    private void validateReferences(UpdateCityMunicipalityCommand command) {
        if (!stateRegionRepository.existsById(command.regionId())) {
            throw new ReferenceNotFoundApplicationException("StateRegion", command.regionId().value());
        }
    }
}

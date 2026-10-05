package springboot.application.citymunicipality.usecase;

import springboot.application.citymunicipality.command.RegisterCityMunicipalityCommand;
import springboot.application.citymunicipality.dto.CityMunicipalityResponse;
import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class RegisterCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;
    private final StateRegionRepository stateRegionRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterCityMunicipalityUseCase(
            CityMunicipalityRepository repository,
            StateRegionRepository stateRegionRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.stateRegionRepository = stateRegionRepository;
        this.eventPublisher = eventPublisher;
    }

    public CityMunicipalityResponse execute(RegisterCityMunicipalityCommand command) {
        validateReferences(command);
        if (repository.existsByRegionIdAndCode(command.regionId(), command.codeCity())) {
            throw new DuplicateResourceApplicationException("CityMunicipality", "codeCity", command.codeCity());
        }
        CityMunicipality aggregate = CityMunicipality.register(
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

    private void validateReferences(RegisterCityMunicipalityCommand command) {
        if (!stateRegionRepository.existsById(command.regionId())) {
            throw new ReferenceNotFoundApplicationException("StateRegion", command.regionId().value());
        }
    }
}

package springboot.infrastructure.citymunicipality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.citymunicipality.usecase.DeleteCityMunicipalityUseCase;
import springboot.application.citymunicipality.usecase.GetCityMunicipalityByIdUseCase;
import springboot.application.citymunicipality.usecase.ListCityMunicipalityUseCase;
import springboot.application.citymunicipality.usecase.RegisterCityMunicipalityUseCase;
import springboot.application.citymunicipality.usecase.UpdateCityMunicipalityUseCase;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.stateregion.port.repository.StateRegionRepository;
import springboot.infrastructure.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;
import springboot.infrastructure.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityJpaRepository;
import springboot.infrastructure.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context CityMunicipality: adaptador de persistencia y casos de uso.
 */
@Configuration
public class CityMunicipalityBeansConfig {

    @Bean
    public CityMunicipalityPersistenceMapper cityMunicipalityPersistenceMapper() {
        return new CityMunicipalityPersistenceMapper();
    }

    @Bean
    public CityMunicipalityRepository cityMunicipalityRepository(CityMunicipalityJpaRepository jpaRepository, CityMunicipalityPersistenceMapper mapper) {
        return new CityMunicipalityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterCityMunicipalityUseCase registerCityMunicipalityUseCase(
            CityMunicipalityRepository repository, StateRegionRepository stateRegionRepository, DomainEventPublisher eventPublisher) {
        return new RegisterCityMunicipalityUseCase(repository, stateRegionRepository, eventPublisher);
    }

    @Bean
    public GetCityMunicipalityByIdUseCase getCityMunicipalityByIdUseCase(CityMunicipalityRepository repository) {
        return new GetCityMunicipalityByIdUseCase(repository);
    }

    @Bean
    public ListCityMunicipalityUseCase listCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new ListCityMunicipalityUseCase(repository);
    }

    @Bean
    public UpdateCityMunicipalityUseCase updateCityMunicipalityUseCase(
            CityMunicipalityRepository repository, StateRegionRepository stateRegionRepository, DomainEventPublisher eventPublisher) {
        return new UpdateCityMunicipalityUseCase(repository, stateRegionRepository, eventPublisher);
    }

    @Bean
    public DeleteCityMunicipalityUseCase deleteCityMunicipalityUseCase(CityMunicipalityRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteCityMunicipalityUseCase(repository, eventPublisher);
    }
}

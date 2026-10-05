package springboot.infrastructure.stateregion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.stateregion.usecase.DeleteStateRegionUseCase;
import springboot.application.stateregion.usecase.GetStateRegionByIdUseCase;
import springboot.application.stateregion.usecase.ListStateRegionUseCase;
import springboot.application.stateregion.usecase.RegisterStateRegionUseCase;
import springboot.application.stateregion.usecase.UpdateStateRegionUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.stateregion.port.repository.StateRegionRepository;
import springboot.infrastructure.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;
import springboot.infrastructure.stateregion.adapters.out.persistence.repositories.StateRegionJpaRepository;
import springboot.infrastructure.stateregion.adapters.out.persistence.repositories.StateRegionRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context StateRegion: adaptador de persistencia y casos de uso.
 */
@Configuration
public class StateRegionBeansConfig {

    @Bean
    public StateRegionPersistenceMapper stateRegionPersistenceMapper() {
        return new StateRegionPersistenceMapper();
    }

    @Bean
    public StateRegionRepository stateRegionRepository(StateRegionJpaRepository jpaRepository, StateRegionPersistenceMapper mapper) {
        return new StateRegionRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterStateRegionUseCase registerStateRegionUseCase(
            StateRegionRepository repository, CountryRepository countryRepository, DomainEventPublisher eventPublisher) {
        return new RegisterStateRegionUseCase(repository, countryRepository, eventPublisher);
    }

    @Bean
    public GetStateRegionByIdUseCase getStateRegionByIdUseCase(StateRegionRepository repository) {
        return new GetStateRegionByIdUseCase(repository);
    }

    @Bean
    public ListStateRegionUseCase listStateRegionUseCase(StateRegionRepository repository) {
        return new ListStateRegionUseCase(repository);
    }

    @Bean
    public UpdateStateRegionUseCase updateStateRegionUseCase(
            StateRegionRepository repository, CountryRepository countryRepository, DomainEventPublisher eventPublisher) {
        return new UpdateStateRegionUseCase(repository, countryRepository, eventPublisher);
    }

    @Bean
    public DeleteStateRegionUseCase deleteStateRegionUseCase(StateRegionRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteStateRegionUseCase(repository, eventPublisher);
    }
}

package springboot.infrastructure.country.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.country.usecase.DeleteCountryUseCase;
import springboot.application.country.usecase.GetCountryByIdUseCase;
import springboot.application.country.usecase.ListCountryUseCase;
import springboot.application.country.usecase.RegisterCountryUseCase;
import springboot.application.country.usecase.UpdateCountryUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.infrastructure.country.adapters.out.persistence.mappers.CountryPersistenceMapper;
import springboot.infrastructure.country.adapters.out.persistence.repositories.CountryJpaRepository;
import springboot.infrastructure.country.adapters.out.persistence.repositories.CountryRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Country: adaptador de persistencia y casos de uso.
 */
@Configuration
public class CountryBeansConfig {

    @Bean
    public CountryPersistenceMapper countryPersistenceMapper() {
        return new CountryPersistenceMapper();
    }

    @Bean
    public CountryRepository countryRepository(CountryJpaRepository jpaRepository, CountryPersistenceMapper mapper) {
        return new CountryRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterCountryUseCase registerCountryUseCase(
            CountryRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterCountryUseCase(repository, eventPublisher);
    }

    @Bean
    public GetCountryByIdUseCase getCountryByIdUseCase(CountryRepository repository) {
        return new GetCountryByIdUseCase(repository);
    }

    @Bean
    public ListCountryUseCase listCountryUseCase(CountryRepository repository) {
        return new ListCountryUseCase(repository);
    }

    @Bean
    public UpdateCountryUseCase updateCountryUseCase(
            CountryRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateCountryUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteCountryUseCase deleteCountryUseCase(CountryRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteCountryUseCase(repository, eventPublisher);
    }
}

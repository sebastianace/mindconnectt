package springboot.infrastructure.professional.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.professional.usecase.DeleteProfessionalUseCase;
import springboot.application.professional.usecase.GetProfessionalByIdUseCase;
import springboot.application.professional.usecase.ListProfessionalUseCase;
import springboot.application.professional.usecase.RegisterProfessionalUseCase;
import springboot.application.professional.usecase.UpdateProfessionalUseCase;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import springboot.infrastructure.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;
import springboot.infrastructure.professional.adapters.out.persistence.repositories.ProfessionalJpaRepository;
import springboot.infrastructure.professional.adapters.out.persistence.repositories.ProfessionalRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Professional: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ProfessionalBeansConfig {

    @Bean
    public ProfessionalPersistenceMapper professionalPersistenceMapper() {
        return new ProfessionalPersistenceMapper();
    }

    @Bean
    public ProfessionalRepository professionalRepository(ProfessionalJpaRepository jpaRepository, ProfessionalPersistenceMapper mapper) {
        return new ProfessionalRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalUseCase registerProfessionalUseCase(
            ProfessionalRepository repository, DocumentTypeRepository documentTypeRepository, ProfessionalTypeRepository professionalTypeRepository, CityMunicipalityRepository cityMunicipalityRepository, DomainEventPublisher eventPublisher) {
        return new RegisterProfessionalUseCase(repository, documentTypeRepository, professionalTypeRepository, cityMunicipalityRepository, eventPublisher);
    }

    @Bean
    public GetProfessionalByIdUseCase getProfessionalByIdUseCase(ProfessionalRepository repository) {
        return new GetProfessionalByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalUseCase listProfessionalUseCase(ProfessionalRepository repository) {
        return new ListProfessionalUseCase(repository);
    }

    @Bean
    public UpdateProfessionalUseCase updateProfessionalUseCase(
            ProfessionalRepository repository, DocumentTypeRepository documentTypeRepository, ProfessionalTypeRepository professionalTypeRepository, CityMunicipalityRepository cityMunicipalityRepository, DomainEventPublisher eventPublisher) {
        return new UpdateProfessionalUseCase(repository, documentTypeRepository, professionalTypeRepository, cityMunicipalityRepository, eventPublisher);
    }

    @Bean
    public DeleteProfessionalUseCase deleteProfessionalUseCase(ProfessionalRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteProfessionalUseCase(repository, eventPublisher);
    }
}

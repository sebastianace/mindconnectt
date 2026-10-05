package springboot.infrastructure.professionaltype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.professionaltype.usecase.DeleteProfessionalTypeUseCase;
import springboot.application.professionaltype.usecase.GetProfessionalTypeByIdUseCase;
import springboot.application.professionaltype.usecase.ListProfessionalTypeUseCase;
import springboot.application.professionaltype.usecase.RegisterProfessionalTypeUseCase;
import springboot.application.professionaltype.usecase.UpdateProfessionalTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import springboot.infrastructure.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;
import springboot.infrastructure.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeJpaRepository;
import springboot.infrastructure.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ProfessionalType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ProfessionalTypeBeansConfig {

    @Bean
    public ProfessionalTypePersistenceMapper professionalTypePersistenceMapper() {
        return new ProfessionalTypePersistenceMapper();
    }

    @Bean
    public ProfessionalTypeRepository professionalTypeRepository(ProfessionalTypeJpaRepository jpaRepository, ProfessionalTypePersistenceMapper mapper) {
        return new ProfessionalTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalTypeUseCase registerProfessionalTypeUseCase(
            ProfessionalTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterProfessionalTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetProfessionalTypeByIdUseCase getProfessionalTypeByIdUseCase(ProfessionalTypeRepository repository) {
        return new GetProfessionalTypeByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalTypeUseCase listProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new ListProfessionalTypeUseCase(repository);
    }

    @Bean
    public UpdateProfessionalTypeUseCase updateProfessionalTypeUseCase(
            ProfessionalTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateProfessionalTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteProfessionalTypeUseCase deleteProfessionalTypeUseCase(ProfessionalTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteProfessionalTypeUseCase(repository, eventPublisher);
    }
}

package springboot.infrastructure.treatmentstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.treatmentstatus.usecase.DeleteTreatmentStatusUseCase;
import springboot.application.treatmentstatus.usecase.GetTreatmentStatusByIdUseCase;
import springboot.application.treatmentstatus.usecase.ListTreatmentStatusUseCase;
import springboot.application.treatmentstatus.usecase.RegisterTreatmentStatusUseCase;
import springboot.application.treatmentstatus.usecase.UpdateTreatmentStatusUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import springboot.infrastructure.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;
import springboot.infrastructure.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusJpaRepository;
import springboot.infrastructure.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context TreatmentStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class TreatmentStatusBeansConfig {

    @Bean
    public TreatmentStatusPersistenceMapper treatmentStatusPersistenceMapper() {
        return new TreatmentStatusPersistenceMapper();
    }

    @Bean
    public TreatmentStatusRepository treatmentStatusRepository(TreatmentStatusJpaRepository jpaRepository, TreatmentStatusPersistenceMapper mapper) {
        return new TreatmentStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentStatusUseCase registerTreatmentStatusUseCase(
            TreatmentStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterTreatmentStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetTreatmentStatusByIdUseCase getTreatmentStatusByIdUseCase(TreatmentStatusRepository repository) {
        return new GetTreatmentStatusByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentStatusUseCase listTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new ListTreatmentStatusUseCase(repository);
    }

    @Bean
    public UpdateTreatmentStatusUseCase updateTreatmentStatusUseCase(
            TreatmentStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateTreatmentStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteTreatmentStatusUseCase deleteTreatmentStatusUseCase(TreatmentStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteTreatmentStatusUseCase(repository, eventPublisher);
    }
}

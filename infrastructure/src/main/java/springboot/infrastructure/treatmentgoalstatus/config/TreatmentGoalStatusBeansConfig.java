package springboot.infrastructure.treatmentgoalstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.treatmentgoalstatus.usecase.DeleteTreatmentGoalStatusUseCase;
import springboot.application.treatmentgoalstatus.usecase.GetTreatmentGoalStatusByIdUseCase;
import springboot.application.treatmentgoalstatus.usecase.ListTreatmentGoalStatusUseCase;
import springboot.application.treatmentgoalstatus.usecase.RegisterTreatmentGoalStatusUseCase;
import springboot.application.treatmentgoalstatus.usecase.UpdateTreatmentGoalStatusUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import springboot.infrastructure.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;
import springboot.infrastructure.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusJpaRepository;
import springboot.infrastructure.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context TreatmentGoalStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class TreatmentGoalStatusBeansConfig {

    @Bean
    public TreatmentGoalStatusPersistenceMapper treatmentGoalStatusPersistenceMapper() {
        return new TreatmentGoalStatusPersistenceMapper();
    }

    @Bean
    public TreatmentGoalStatusRepository treatmentGoalStatusRepository(TreatmentGoalStatusJpaRepository jpaRepository, TreatmentGoalStatusPersistenceMapper mapper) {
        return new TreatmentGoalStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentGoalStatusUseCase registerTreatmentGoalStatusUseCase(
            TreatmentGoalStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterTreatmentGoalStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetTreatmentGoalStatusByIdUseCase getTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) {
        return new GetTreatmentGoalStatusByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentGoalStatusUseCase listTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new ListTreatmentGoalStatusUseCase(repository);
    }

    @Bean
    public UpdateTreatmentGoalStatusUseCase updateTreatmentGoalStatusUseCase(
            TreatmentGoalStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateTreatmentGoalStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteTreatmentGoalStatusUseCase deleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteTreatmentGoalStatusUseCase(repository, eventPublisher);
    }
}

package springboot.infrastructure.treatmentgoal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.treatmentgoal.usecase.DeleteTreatmentGoalUseCase;
import springboot.application.treatmentgoal.usecase.GetTreatmentGoalByIdUseCase;
import springboot.application.treatmentgoal.usecase.ListTreatmentGoalUseCase;
import springboot.application.treatmentgoal.usecase.RegisterTreatmentGoalUseCase;
import springboot.application.treatmentgoal.usecase.UpdateTreatmentGoalUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import springboot.infrastructure.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;
import springboot.infrastructure.treatmentgoal.adapters.out.persistence.repositories.TreatmentGoalJpaRepository;
import springboot.infrastructure.treatmentgoal.adapters.out.persistence.repositories.TreatmentGoalRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context TreatmentGoal: adaptador de persistencia y casos de uso.
 */
@Configuration
public class TreatmentGoalBeansConfig {

    @Bean
    public TreatmentGoalPersistenceMapper treatmentGoalPersistenceMapper() {
        return new TreatmentGoalPersistenceMapper();
    }

    @Bean
    public TreatmentGoalRepository treatmentGoalRepository(TreatmentGoalJpaRepository jpaRepository, TreatmentGoalPersistenceMapper mapper) {
        return new TreatmentGoalRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentGoalUseCase registerTreatmentGoalUseCase(
            TreatmentGoalRepository repository, TreatmentPlanRepository treatmentPlanRepository, TreatmentGoalStatusRepository treatmentGoalStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterTreatmentGoalUseCase(repository, treatmentPlanRepository, treatmentGoalStatusRepository, eventPublisher);
    }

    @Bean
    public GetTreatmentGoalByIdUseCase getTreatmentGoalByIdUseCase(TreatmentGoalRepository repository) {
        return new GetTreatmentGoalByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentGoalUseCase listTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new ListTreatmentGoalUseCase(repository);
    }

    @Bean
    public UpdateTreatmentGoalUseCase updateTreatmentGoalUseCase(
            TreatmentGoalRepository repository, TreatmentPlanRepository treatmentPlanRepository, TreatmentGoalStatusRepository treatmentGoalStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateTreatmentGoalUseCase(repository, treatmentPlanRepository, treatmentGoalStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteTreatmentGoalUseCase deleteTreatmentGoalUseCase(TreatmentGoalRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteTreatmentGoalUseCase(repository, eventPublisher);
    }
}

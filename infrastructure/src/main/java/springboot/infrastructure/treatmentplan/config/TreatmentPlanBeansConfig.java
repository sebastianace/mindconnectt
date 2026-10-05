package springboot.infrastructure.treatmentplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.treatmentplan.usecase.DeleteTreatmentPlanUseCase;
import springboot.application.treatmentplan.usecase.GetTreatmentPlanByIdUseCase;
import springboot.application.treatmentplan.usecase.ListTreatmentPlanUseCase;
import springboot.application.treatmentplan.usecase.RegisterTreatmentPlanUseCase;
import springboot.application.treatmentplan.usecase.UpdateTreatmentPlanUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import springboot.infrastructure.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;
import springboot.infrastructure.treatmentplan.adapters.out.persistence.repositories.TreatmentPlanJpaRepository;
import springboot.infrastructure.treatmentplan.adapters.out.persistence.repositories.TreatmentPlanRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context TreatmentPlan: adaptador de persistencia y casos de uso.
 */
@Configuration
public class TreatmentPlanBeansConfig {

    @Bean
    public TreatmentPlanPersistenceMapper treatmentPlanPersistenceMapper() {
        return new TreatmentPlanPersistenceMapper();
    }

    @Bean
    public TreatmentPlanRepository treatmentPlanRepository(TreatmentPlanJpaRepository jpaRepository, TreatmentPlanPersistenceMapper mapper) {
        return new TreatmentPlanRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentPlanUseCase registerTreatmentPlanUseCase(
            TreatmentPlanRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, TreatmentStatusRepository treatmentStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterTreatmentPlanUseCase(repository, encounterRepository, professionalRepository, treatmentStatusRepository, eventPublisher);
    }

    @Bean
    public GetTreatmentPlanByIdUseCase getTreatmentPlanByIdUseCase(TreatmentPlanRepository repository) {
        return new GetTreatmentPlanByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentPlanUseCase listTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new ListTreatmentPlanUseCase(repository);
    }

    @Bean
    public UpdateTreatmentPlanUseCase updateTreatmentPlanUseCase(
            TreatmentPlanRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, TreatmentStatusRepository treatmentStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateTreatmentPlanUseCase(repository, encounterRepository, professionalRepository, treatmentStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteTreatmentPlanUseCase deleteTreatmentPlanUseCase(TreatmentPlanRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteTreatmentPlanUseCase(repository, eventPublisher);
    }
}

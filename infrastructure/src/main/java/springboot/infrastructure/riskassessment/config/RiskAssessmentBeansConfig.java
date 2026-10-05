package springboot.infrastructure.riskassessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.riskassessment.usecase.DeleteRiskAssessmentUseCase;
import springboot.application.riskassessment.usecase.GetRiskAssessmentByIdUseCase;
import springboot.application.riskassessment.usecase.ListRiskAssessmentUseCase;
import springboot.application.riskassessment.usecase.RegisterRiskAssessmentUseCase;
import springboot.application.riskassessment.usecase.UpdateRiskAssessmentUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.riskassessment.port.repository.RiskAssessmentRepository;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;
import springboot.infrastructure.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;
import springboot.infrastructure.riskassessment.adapters.out.persistence.repositories.RiskAssessmentJpaRepository;
import springboot.infrastructure.riskassessment.adapters.out.persistence.repositories.RiskAssessmentRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context RiskAssessment: adaptador de persistencia y casos de uso.
 */
@Configuration
public class RiskAssessmentBeansConfig {

    @Bean
    public RiskAssessmentPersistenceMapper riskAssessmentPersistenceMapper() {
        return new RiskAssessmentPersistenceMapper();
    }

    @Bean
    public RiskAssessmentRepository riskAssessmentRepository(RiskAssessmentJpaRepository jpaRepository, RiskAssessmentPersistenceMapper mapper) {
        return new RiskAssessmentRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRiskAssessmentUseCase registerRiskAssessmentUseCase(
            RiskAssessmentRepository repository, EncounterRepository encounterRepository, RiskLevelRepository riskLevelRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterRiskAssessmentUseCase(repository, encounterRepository, riskLevelRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetRiskAssessmentByIdUseCase getRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        return new GetRiskAssessmentByIdUseCase(repository);
    }

    @Bean
    public ListRiskAssessmentUseCase listRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new ListRiskAssessmentUseCase(repository);
    }

    @Bean
    public UpdateRiskAssessmentUseCase updateRiskAssessmentUseCase(
            RiskAssessmentRepository repository, EncounterRepository encounterRepository, RiskLevelRepository riskLevelRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateRiskAssessmentUseCase(repository, encounterRepository, riskLevelRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteRiskAssessmentUseCase deleteRiskAssessmentUseCase(RiskAssessmentRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteRiskAssessmentUseCase(repository, eventPublisher);
    }
}

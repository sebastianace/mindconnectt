package springboot.infrastructure.risklevel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.risklevel.usecase.DeleteRiskLevelUseCase;
import springboot.application.risklevel.usecase.GetRiskLevelByIdUseCase;
import springboot.application.risklevel.usecase.ListRiskLevelUseCase;
import springboot.application.risklevel.usecase.RegisterRiskLevelUseCase;
import springboot.application.risklevel.usecase.UpdateRiskLevelUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;
import springboot.infrastructure.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
import springboot.infrastructure.risklevel.adapters.out.persistence.repositories.RiskLevelJpaRepository;
import springboot.infrastructure.risklevel.adapters.out.persistence.repositories.RiskLevelRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context RiskLevel: adaptador de persistencia y casos de uso.
 */
@Configuration
public class RiskLevelBeansConfig {

    @Bean
    public RiskLevelPersistenceMapper riskLevelPersistenceMapper() {
        return new RiskLevelPersistenceMapper();
    }

    @Bean
    public RiskLevelRepository riskLevelRepository(RiskLevelJpaRepository jpaRepository, RiskLevelPersistenceMapper mapper) {
        return new RiskLevelRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRiskLevelUseCase registerRiskLevelUseCase(
            RiskLevelRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterRiskLevelUseCase(repository, eventPublisher);
    }

    @Bean
    public GetRiskLevelByIdUseCase getRiskLevelByIdUseCase(RiskLevelRepository repository) {
        return new GetRiskLevelByIdUseCase(repository);
    }

    @Bean
    public ListRiskLevelUseCase listRiskLevelUseCase(RiskLevelRepository repository) {
        return new ListRiskLevelUseCase(repository);
    }

    @Bean
    public UpdateRiskLevelUseCase updateRiskLevelUseCase(
            RiskLevelRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateRiskLevelUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteRiskLevelUseCase deleteRiskLevelUseCase(RiskLevelRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteRiskLevelUseCase(repository, eventPublisher);
    }
}

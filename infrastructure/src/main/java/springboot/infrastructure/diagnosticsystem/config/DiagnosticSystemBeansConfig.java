package springboot.infrastructure.diagnosticsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
import springboot.application.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import springboot.application.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import springboot.application.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import springboot.application.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import springboot.infrastructure.diagnosticsystem.adapters.out.persistence.mappers.DiagnosticSystemPersistenceMapper;
import springboot.infrastructure.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemJpaRepository;
import springboot.infrastructure.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context DiagnosticSystem: adaptador de persistencia y casos de uso.
 */
@Configuration
public class DiagnosticSystemBeansConfig {

    @Bean
    public DiagnosticSystemPersistenceMapper diagnosticSystemPersistenceMapper() {
        return new DiagnosticSystemPersistenceMapper();
    }

    @Bean
    public DiagnosticSystemRepository diagnosticSystemRepository(DiagnosticSystemJpaRepository jpaRepository, DiagnosticSystemPersistenceMapper mapper) {
        return new DiagnosticSystemRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterDiagnosticSystemUseCase registerDiagnosticSystemUseCase(
            DiagnosticSystemRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterDiagnosticSystemUseCase(repository, eventPublisher);
    }

    @Bean
    public GetDiagnosticSystemByIdUseCase getDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) {
        return new GetDiagnosticSystemByIdUseCase(repository);
    }

    @Bean
    public ListDiagnosticSystemUseCase listDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new ListDiagnosticSystemUseCase(repository);
    }

    @Bean
    public UpdateDiagnosticSystemUseCase updateDiagnosticSystemUseCase(
            DiagnosticSystemRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateDiagnosticSystemUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteDiagnosticSystemUseCase deleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteDiagnosticSystemUseCase(repository, eventPublisher);
    }
}

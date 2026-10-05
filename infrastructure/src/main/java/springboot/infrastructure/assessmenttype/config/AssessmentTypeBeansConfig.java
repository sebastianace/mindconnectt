package springboot.infrastructure.assessmenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
import springboot.application.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import springboot.application.assessmenttype.usecase.ListAssessmentTypeUseCase;
import springboot.application.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import springboot.application.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;
import springboot.infrastructure.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeJpaRepository;
import springboot.infrastructure.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context AssessmentType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class AssessmentTypeBeansConfig {

    @Bean
    public AssessmentTypePersistenceMapper assessmentTypePersistenceMapper() {
        return new AssessmentTypePersistenceMapper();
    }

    @Bean
    public AssessmentTypeRepository assessmentTypeRepository(AssessmentTypeJpaRepository jpaRepository, AssessmentTypePersistenceMapper mapper) {
        return new AssessmentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterAssessmentTypeUseCase registerAssessmentTypeUseCase(
            AssessmentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterAssessmentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetAssessmentTypeByIdUseCase getAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) {
        return new GetAssessmentTypeByIdUseCase(repository);
    }

    @Bean
    public ListAssessmentTypeUseCase listAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new ListAssessmentTypeUseCase(repository);
    }

    @Bean
    public UpdateAssessmentTypeUseCase updateAssessmentTypeUseCase(
            AssessmentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateAssessmentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteAssessmentTypeUseCase deleteAssessmentTypeUseCase(AssessmentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteAssessmentTypeUseCase(repository, eventPublisher);
    }
}

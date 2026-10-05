package springboot.infrastructure.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.study.usecase.DeleteStudyUseCase;
import springboot.application.study.usecase.GetStudyByIdUseCase;
import springboot.application.study.usecase.ListStudyUseCase;
import springboot.application.study.usecase.RegisterStudyUseCase;
import springboot.application.study.usecase.UpdateStudyUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.study.port.repository.StudyRepository;
import springboot.infrastructure.study.adapters.out.persistence.mappers.StudyPersistenceMapper;
import springboot.infrastructure.study.adapters.out.persistence.repositories.StudyJpaRepository;
import springboot.infrastructure.study.adapters.out.persistence.repositories.StudyRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Study: adaptador de persistencia y casos de uso.
 */
@Configuration
public class StudyBeansConfig {

    @Bean
    public StudyPersistenceMapper studyPersistenceMapper() {
        return new StudyPersistenceMapper();
    }

    @Bean
    public StudyRepository studyRepository(StudyJpaRepository jpaRepository, StudyPersistenceMapper mapper) {
        return new StudyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterStudyUseCase registerStudyUseCase(
            StudyRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterStudyUseCase(repository, eventPublisher);
    }

    @Bean
    public GetStudyByIdUseCase getStudyByIdUseCase(StudyRepository repository) {
        return new GetStudyByIdUseCase(repository);
    }

    @Bean
    public ListStudyUseCase listStudyUseCase(StudyRepository repository) {
        return new ListStudyUseCase(repository);
    }

    @Bean
    public UpdateStudyUseCase updateStudyUseCase(
            StudyRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateStudyUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteStudyUseCase deleteStudyUseCase(StudyRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteStudyUseCase(repository, eventPublisher);
    }
}

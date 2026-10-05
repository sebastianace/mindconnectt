package springboot.infrastructure.professionalstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.professionalstudy.usecase.DeleteProfessionalStudyUseCase;
import springboot.application.professionalstudy.usecase.GetProfessionalStudyByIdUseCase;
import springboot.application.professionalstudy.usecase.ListProfessionalStudyUseCase;
import springboot.application.professionalstudy.usecase.RegisterProfessionalStudyUseCase;
import springboot.application.professionalstudy.usecase.UpdateProfessionalStudyUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import springboot.domain.study.port.repository.StudyRepository;
import springboot.infrastructure.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;
import springboot.infrastructure.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyJpaRepository;
import springboot.infrastructure.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ProfessionalStudy: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ProfessionalStudyBeansConfig {

    @Bean
    public ProfessionalStudyPersistenceMapper professionalStudyPersistenceMapper() {
        return new ProfessionalStudyPersistenceMapper();
    }

    @Bean
    public ProfessionalStudyRepository professionalStudyRepository(ProfessionalStudyJpaRepository jpaRepository, ProfessionalStudyPersistenceMapper mapper) {
        return new ProfessionalStudyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalStudyUseCase registerProfessionalStudyUseCase(
            ProfessionalStudyRepository repository, StudyRepository studyRepository, ProfessionalRepository professionalRepository, CountryRepository countryRepository, DomainEventPublisher eventPublisher) {
        return new RegisterProfessionalStudyUseCase(repository, studyRepository, professionalRepository, countryRepository, eventPublisher);
    }

    @Bean
    public GetProfessionalStudyByIdUseCase getProfessionalStudyByIdUseCase(ProfessionalStudyRepository repository) {
        return new GetProfessionalStudyByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalStudyUseCase listProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new ListProfessionalStudyUseCase(repository);
    }

    @Bean
    public UpdateProfessionalStudyUseCase updateProfessionalStudyUseCase(
            ProfessionalStudyRepository repository, StudyRepository studyRepository, ProfessionalRepository professionalRepository, CountryRepository countryRepository, DomainEventPublisher eventPublisher) {
        return new UpdateProfessionalStudyUseCase(repository, studyRepository, professionalRepository, countryRepository, eventPublisher);
    }

    @Bean
    public DeleteProfessionalStudyUseCase deleteProfessionalStudyUseCase(ProfessionalStudyRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteProfessionalStudyUseCase(repository, eventPublisher);
    }
}

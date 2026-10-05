package springboot.application.professionalstudy.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.professionalstudy.command.RegisterProfessionalStudyCommand;
import springboot.application.professionalstudy.dto.ProfessionalStudyResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import springboot.domain.study.port.repository.StudyRepository;

public class RegisterProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;
    private final StudyRepository studyRepository;
    private final ProfessionalRepository professionalRepository;
    private final CountryRepository countryRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterProfessionalStudyUseCase(
            ProfessionalStudyRepository repository,
            StudyRepository studyRepository,
            ProfessionalRepository professionalRepository,
            CountryRepository countryRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.studyRepository = studyRepository;
        this.professionalRepository = professionalRepository;
        this.countryRepository = countryRepository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalStudyResponse execute(RegisterProfessionalStudyCommand command) {
        validateReferences(command);
        ProfessionalStudy aggregate = ProfessionalStudy.register(
                command.studyId(),
                command.professionalId(),
                command.title(),
                command.university(),
                command.valid(),
                command.resolutionNumber(),
                command.countryId());
        ProfessionalStudy saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ProfessionalStudyResponse.from(saved);
    }

    private void validateReferences(RegisterProfessionalStudyCommand command) {
        if (!studyRepository.existsById(command.studyId())) {
            throw new ReferenceNotFoundApplicationException("Study", command.studyId().value());
        }
        if (!professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
        if (!countryRepository.existsById(command.countryId())) {
            throw new ReferenceNotFoundApplicationException("Country", command.countryId().value());
        }
    }
}

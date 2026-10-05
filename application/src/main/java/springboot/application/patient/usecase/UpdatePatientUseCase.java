package springboot.application.patient.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.patient.command.UpdatePatientCommand;
import springboot.application.patient.dto.PatientResponse;
import springboot.application.patient.exception.PatientNotFoundApplicationException;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;
import springboot.domain.gender.port.repository.GenderRepository;
import springboot.domain.patient.model.aggregate.Patient;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class UpdatePatientUseCase {
    private final PatientRepository repository;
    private final DocumentTypeRepository documentTypeRepository;
    private final GenderRepository genderRepository;
    private final ProfessionalRepository professionalRepository;
    private final CityMunicipalityRepository cityMunicipalityRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdatePatientUseCase(
            PatientRepository repository,
            DocumentTypeRepository documentTypeRepository,
            GenderRepository genderRepository,
            ProfessionalRepository professionalRepository,
            CityMunicipalityRepository cityMunicipalityRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.documentTypeRepository = documentTypeRepository;
        this.genderRepository = genderRepository;
        this.professionalRepository = professionalRepository;
        this.cityMunicipalityRepository = cityMunicipalityRepository;
        this.eventPublisher = eventPublisher;
    }

    public PatientResponse execute(UpdatePatientCommand command) {
        Patient aggregate = repository.findById(command.id())
                .orElseThrow(() -> new PatientNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.documentTypeId(),
                command.documentNumber(),
                command.firstName(),
                command.middleName(),
                command.lastName(),
                command.secondLastName(),
                command.birthDate(),
                command.biologicalSexId(),
                command.genderIdentityId(),
                command.email(),
                command.phone(),
                command.address(),
                command.active(),
                command.createdBy(),
                command.updatedBy(),
                command.cityId());
        Patient saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PatientResponse.from(saved);
    }

    private void validateReferences(UpdatePatientCommand command) {
        if (!documentTypeRepository.existsById(command.documentTypeId())) {
            throw new ReferenceNotFoundApplicationException("DocumentType", command.documentTypeId().value());
        }
        if (!genderRepository.existsById(command.biologicalSexId())) {
            throw new ReferenceNotFoundApplicationException("Gender", command.biologicalSexId().value());
        }
        if (!genderRepository.existsById(command.genderIdentityId())) {
            throw new ReferenceNotFoundApplicationException("Gender", command.genderIdentityId().value());
        }
        if (command.createdBy() != null && !professionalRepository.existsById(command.createdBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.createdBy().value());
        }
        if (command.updatedBy() != null && !professionalRepository.existsById(command.updatedBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.updatedBy().value());
        }
        if (!cityMunicipalityRepository.existsById(command.cityId())) {
            throw new ReferenceNotFoundApplicationException("CityMunicipality", command.cityId().value());
        }
    }
}

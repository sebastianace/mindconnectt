package springboot.application.professional.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.professional.command.UpdateProfessionalCommand;
import springboot.application.professional.dto.ProfessionalResponse;
import springboot.application.professional.exception.ProfessionalNotFoundApplicationException;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;
import springboot.domain.professional.model.aggregate.Professional;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

public class UpdateProfessionalUseCase {
    private final ProfessionalRepository repository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ProfessionalTypeRepository professionalTypeRepository;
    private final CityMunicipalityRepository cityMunicipalityRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateProfessionalUseCase(
            ProfessionalRepository repository,
            DocumentTypeRepository documentTypeRepository,
            ProfessionalTypeRepository professionalTypeRepository,
            CityMunicipalityRepository cityMunicipalityRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.documentTypeRepository = documentTypeRepository;
        this.professionalTypeRepository = professionalTypeRepository;
        this.cityMunicipalityRepository = cityMunicipalityRepository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalResponse execute(UpdateProfessionalCommand command) {
        Professional aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.documentTypeId(),
                command.documentNumber(),
                command.firstName(),
                command.lastName(),
                command.professionalTypeId(),
                command.licenseNumber(),
                command.active(),
                command.cityId());
        Professional saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ProfessionalResponse.from(saved);
    }

    private void validateReferences(UpdateProfessionalCommand command) {
        if (!documentTypeRepository.existsById(command.documentTypeId())) {
            throw new ReferenceNotFoundApplicationException("DocumentType", command.documentTypeId().value());
        }
        if (!professionalTypeRepository.existsById(command.professionalTypeId())) {
            throw new ReferenceNotFoundApplicationException("ProfessionalType", command.professionalTypeId().value());
        }
        if (!cityMunicipalityRepository.existsById(command.cityId())) {
            throw new ReferenceNotFoundApplicationException("CityMunicipality", command.cityId().value());
        }
    }
}

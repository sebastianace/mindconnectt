package springboot.application.contact.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.contact.command.RegisterContactCommand;
import springboot.application.contact.dto.ContactResponse;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.model.aggregate.Contact;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class RegisterContactUseCase {
    private final ContactRepository repository;
    private final CityMunicipalityRepository cityMunicipalityRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterContactUseCase(
            ContactRepository repository,
            CityMunicipalityRepository cityMunicipalityRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.cityMunicipalityRepository = cityMunicipalityRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public ContactResponse execute(RegisterContactCommand command) {
        validateReferences(command);
        Contact aggregate = Contact.register(
                command.fullName(),
                command.email(),
                command.notes(),
                command.cityId(),
                command.createdBy(),
                command.updatedBy());
        Contact saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ContactResponse.from(saved);
    }

    private void validateReferences(RegisterContactCommand command) {
        if (!cityMunicipalityRepository.existsById(command.cityId())) {
            throw new ReferenceNotFoundApplicationException("CityMunicipality", command.cityId().value());
        }
        if (!professionalRepository.existsById(command.createdBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.createdBy().value());
        }
        if (command.updatedBy() != null && !professionalRepository.existsById(command.updatedBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.updatedBy().value());
        }
    }
}

package springboot.application.patientallergy.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.patientallergy.command.RegisterPatientAllergyCommand;
import springboot.application.patientallergy.dto.PatientAllergyResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.patientallergy.model.aggregate.PatientAllergy;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class RegisterPatientAllergyUseCase {
    private final PatientAllergyRepository repository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterPatientAllergyUseCase(
            PatientAllergyRepository repository,
            PatientRepository patientRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public PatientAllergyResponse execute(RegisterPatientAllergyCommand command) {
        validateReferences(command);
        PatientAllergy aggregate = PatientAllergy.register(
                command.patientId(),
                command.substance(),
                command.reaction(),
                command.severity(),
                command.active(),
                command.recordedAt(),
                command.recordedBy());
        PatientAllergy saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return PatientAllergyResponse.from(saved);
    }

    private void validateReferences(RegisterPatientAllergyCommand command) {
        if (!patientRepository.existsById(command.patientId())) {
            throw new ReferenceNotFoundApplicationException("Patient", command.patientId().value());
        }
        if (!professionalRepository.existsById(command.recordedBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.recordedBy().value());
        }
    }
}

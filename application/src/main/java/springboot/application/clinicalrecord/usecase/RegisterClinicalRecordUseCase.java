package springboot.application.clinicalrecord.usecase;

import springboot.application.clinicalrecord.command.RegisterClinicalRecordCommand;
import springboot.application.clinicalrecord.dto.ClinicalRecordResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class RegisterClinicalRecordUseCase {
    private final ClinicalRecordRepository repository;
    private final PatientRepository patientRepository;
    private final ClinicalRecordStatusRepository clinicalRecordStatusRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterClinicalRecordUseCase(
            ClinicalRecordRepository repository,
            PatientRepository patientRepository,
            ClinicalRecordStatusRepository clinicalRecordStatusRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.clinicalRecordStatusRepository = clinicalRecordStatusRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalRecordResponse execute(RegisterClinicalRecordCommand command) {
        validateReferences(command);
        ClinicalRecord aggregate = ClinicalRecord.register(
                command.patientId(),
                command.creationDate(),
                command.recordNumber(),
                command.openedAt(),
                command.closedAt(),
                command.statusId(),
                command.createdBy());
        ClinicalRecord saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ClinicalRecordResponse.from(saved);
    }

    private void validateReferences(RegisterClinicalRecordCommand command) {
        if (!patientRepository.existsById(command.patientId())) {
            throw new ReferenceNotFoundApplicationException("Patient", command.patientId().value());
        }
        if (!clinicalRecordStatusRepository.existsById(command.statusId())) {
            throw new ReferenceNotFoundApplicationException("ClinicalRecordStatus", command.statusId().value());
        }
        if (!professionalRepository.existsById(command.createdBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.createdBy().value());
        }
    }
}

package springboot.application.encounter.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.encounter.command.RegisterEncounterCommand;
import springboot.application.encounter.dto.EncounterResponse;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.model.aggregate.Encounter;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class RegisterEncounterUseCase {
    private final EncounterRepository repository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final ProfessionalRepository professionalRepository;
    private final EncounterTypeRepository encounterTypeRepository;
    private final EncounterModalityRepository encounterModalityRepository;
    private final EncounterStatusRepository encounterStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterEncounterUseCase(
            EncounterRepository repository,
            ClinicalRecordRepository clinicalRecordRepository,
            ProfessionalRepository professionalRepository,
            EncounterTypeRepository encounterTypeRepository,
            EncounterModalityRepository encounterModalityRepository,
            EncounterStatusRepository encounterStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.professionalRepository = professionalRepository;
        this.encounterTypeRepository = encounterTypeRepository;
        this.encounterModalityRepository = encounterModalityRepository;
        this.encounterStatusRepository = encounterStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public EncounterResponse execute(RegisterEncounterCommand command) {
        validateReferences(command);
        Encounter aggregate = Encounter.register(
                command.clinicalRecordId(),
                command.professionalId(),
                command.encounterTypeId(),
                command.startedAt(),
                command.endedAt(),
                command.reasonForVisit(),
                command.currentCondition(),
                command.modalityId(),
                command.statusId(),
                command.createdBy(),
                command.updatedBy());
        Encounter saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return EncounterResponse.from(saved);
    }

    private void validateReferences(RegisterEncounterCommand command) {
        if (!clinicalRecordRepository.existsById(command.clinicalRecordId())) {
            throw new ReferenceNotFoundApplicationException("ClinicalRecord", command.clinicalRecordId().value());
        }
        if (!professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
        if (!encounterTypeRepository.existsById(command.encounterTypeId())) {
            throw new ReferenceNotFoundApplicationException("EncounterType", command.encounterTypeId().value());
        }
        if (!encounterModalityRepository.existsById(command.modalityId())) {
            throw new ReferenceNotFoundApplicationException("EncounterModality", command.modalityId().value());
        }
        if (!encounterStatusRepository.existsById(command.statusId())) {
            throw new ReferenceNotFoundApplicationException("EncounterStatus", command.statusId().value());
        }
        if (!professionalRepository.existsById(command.createdBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.createdBy().value());
        }
        if (!professionalRepository.existsById(command.updatedBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.updatedBy().value());
        }
    }
}

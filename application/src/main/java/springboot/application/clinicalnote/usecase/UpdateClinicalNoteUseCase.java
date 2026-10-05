package springboot.application.clinicalnote.usecase;

import springboot.application.clinicalnote.command.UpdateClinicalNoteCommand;
import springboot.application.clinicalnote.dto.ClinicalNoteResponse;
import springboot.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.clinicalnote.model.aggregate.ClinicalNote;
import springboot.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class UpdateClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;
    private final EncounterRepository encounterRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateClinicalNoteUseCase(
            ClinicalNoteRepository repository,
            EncounterRepository encounterRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.encounterRepository = encounterRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalNoteResponse execute(UpdateClinicalNoteCommand command) {
        ClinicalNote aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.encounterId(),
                command.professionalId(),
                command.subjective(),
                command.objective(),
                command.assessment(),
                command.plan(),
                command.additionalNotes(),
                command.signedAt());
        ClinicalNote saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ClinicalNoteResponse.from(saved);
    }

    private void validateReferences(UpdateClinicalNoteCommand command) {
        if (!encounterRepository.existsById(command.encounterId())) {
            throw new ReferenceNotFoundApplicationException("Encounter", command.encounterId().value());
        }
        if (!professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
    }
}

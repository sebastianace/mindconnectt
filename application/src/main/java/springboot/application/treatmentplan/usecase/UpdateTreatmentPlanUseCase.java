package springboot.application.treatmentplan.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.treatmentplan.command.UpdateTreatmentPlanCommand;
import springboot.application.treatmentplan.dto.TreatmentPlanResponse;
import springboot.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.treatmentplan.model.aggregate.TreatmentPlan;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class UpdateTreatmentPlanUseCase {
    private final TreatmentPlanRepository repository;
    private final EncounterRepository encounterRepository;
    private final ProfessionalRepository professionalRepository;
    private final TreatmentStatusRepository treatmentStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateTreatmentPlanUseCase(
            TreatmentPlanRepository repository,
            EncounterRepository encounterRepository,
            ProfessionalRepository professionalRepository,
            TreatmentStatusRepository treatmentStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.encounterRepository = encounterRepository;
        this.professionalRepository = professionalRepository;
        this.treatmentStatusRepository = treatmentStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentPlanResponse execute(UpdateTreatmentPlanCommand command) {
        TreatmentPlan aggregate = repository.findById(command.id())
                .orElseThrow(() -> new TreatmentPlanNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.encounterId(),
                command.professionalId(),
                command.title(),
                command.description(),
                command.startDate(),
                command.endDate(),
                command.treatmentStatusId());
        TreatmentPlan saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentPlanResponse.from(saved);
    }

    private void validateReferences(UpdateTreatmentPlanCommand command) {
        if (!encounterRepository.existsById(command.encounterId())) {
            throw new ReferenceNotFoundApplicationException("Encounter", command.encounterId().value());
        }
        if (!professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
        if (!treatmentStatusRepository.existsById(command.treatmentStatusId())) {
            throw new ReferenceNotFoundApplicationException("TreatmentStatus", command.treatmentStatusId().value());
        }
    }
}

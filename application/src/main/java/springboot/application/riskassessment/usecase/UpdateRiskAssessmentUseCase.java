package springboot.application.riskassessment.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.riskassessment.command.UpdateRiskAssessmentCommand;
import springboot.application.riskassessment.dto.RiskAssessmentResponse;
import springboot.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.riskassessment.model.aggregate.RiskAssessment;
import springboot.domain.riskassessment.port.repository.RiskAssessmentRepository;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class UpdateRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;
    private final EncounterRepository encounterRepository;
    private final RiskLevelRepository riskLevelRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateRiskAssessmentUseCase(
            RiskAssessmentRepository repository,
            EncounterRepository encounterRepository,
            RiskLevelRepository riskLevelRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.encounterRepository = encounterRepository;
        this.riskLevelRepository = riskLevelRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public RiskAssessmentResponse execute(UpdateRiskAssessmentCommand command) {
        RiskAssessment aggregate = repository.findById(command.id())
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.encounterId(),
                command.riskLevelId(),
                command.suicidalIdeation(),
                command.suicidePlan(),
                command.suicideIntent(),
                command.selfHarm(),
                command.harmToOthers(),
                command.riskFactors(),
                command.protectiveFactors(),
                command.clinicalActions(),
                command.observations(),
                command.assessedAt(),
                command.assessedBy());
        RiskAssessment saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return RiskAssessmentResponse.from(saved);
    }

    private void validateReferences(UpdateRiskAssessmentCommand command) {
        if (!encounterRepository.existsById(command.encounterId())) {
            throw new ReferenceNotFoundApplicationException("Encounter", command.encounterId().value());
        }
        if (!riskLevelRepository.existsById(command.riskLevelId())) {
            throw new ReferenceNotFoundApplicationException("RiskLevel", command.riskLevelId().value());
        }
        if (!professionalRepository.existsById(command.assessedBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.assessedBy().value());
        }
    }
}

package springboot.application.treatmentgoal.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.treatmentgoal.command.RegisterTreatmentGoalCommand;
import springboot.application.treatmentgoal.dto.TreatmentGoalResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;

public class RegisterTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentGoalStatusRepository treatmentGoalStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterTreatmentGoalUseCase(
            TreatmentGoalRepository repository,
            TreatmentPlanRepository treatmentPlanRepository,
            TreatmentGoalStatusRepository treatmentGoalStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentGoalStatusRepository = treatmentGoalStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalResponse execute(RegisterTreatmentGoalCommand command) {
        validateReferences(command);
        TreatmentGoal aggregate = TreatmentGoal.register(
                command.treatmentPlanId(),
                command.description(),
                command.targetDate(),
                command.completedAt(),
                command.notes(),
                command.treatmentGoalStatusId());
        TreatmentGoal saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentGoalResponse.from(saved);
    }

    private void validateReferences(RegisterTreatmentGoalCommand command) {
        if (!treatmentPlanRepository.existsById(command.treatmentPlanId())) {
            throw new ReferenceNotFoundApplicationException("TreatmentPlan", command.treatmentPlanId().value());
        }
        if (!treatmentGoalStatusRepository.existsById(command.treatmentGoalStatusId())) {
            throw new ReferenceNotFoundApplicationException("TreatmentGoalStatus", command.treatmentGoalStatusId().value());
        }
    }
}

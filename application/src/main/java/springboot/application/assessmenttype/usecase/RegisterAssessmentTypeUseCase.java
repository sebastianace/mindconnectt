package springboot.application.assessmenttype.usecase;

import springboot.application.assessmenttype.command.RegisterAssessmentTypeCommand;
import springboot.application.assessmenttype.dto.AssessmentTypeResponse;
import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.domain.assessmenttype.model.aggregate.AssessmentType;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterAssessmentTypeUseCase(
            AssessmentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AssessmentTypeResponse execute(RegisterAssessmentTypeCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("AssessmentType", "code", command.code());
        }
        AssessmentType aggregate = AssessmentType.register(
                command.code(),
                command.name(),
                command.active(),
                command.description());
        AssessmentType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return AssessmentTypeResponse.from(saved);
    }
}

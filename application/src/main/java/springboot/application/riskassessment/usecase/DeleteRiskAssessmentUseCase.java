package springboot.application.riskassessment.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.riskassessment.event.RiskAssessmentDeletedEvent;
import springboot.domain.riskassessment.model.aggregate.RiskAssessment;
import springboot.domain.riskassessment.model.valueobject.RiskAssessmentId;
import springboot.domain.riskassessment.port.repository.RiskAssessmentRepository;

public class DeleteRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteRiskAssessmentUseCase(RiskAssessmentRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RiskAssessmentDeletedEvent execute(RiskAssessmentId id) {
        RiskAssessment aggregate = repository.findById(id)
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        RiskAssessmentDeletedEvent event = new RiskAssessmentDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

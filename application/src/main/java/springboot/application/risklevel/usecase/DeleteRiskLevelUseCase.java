package springboot.application.risklevel.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.risklevel.event.RiskLevelDeletedEvent;
import springboot.domain.risklevel.model.aggregate.RiskLevel;
import springboot.domain.risklevel.model.valueobject.RiskLevelId;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class DeleteRiskLevelUseCase {
    private final RiskLevelRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteRiskLevelUseCase(RiskLevelRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RiskLevelDeletedEvent execute(RiskLevelId id) {
        RiskLevel aggregate = repository.findById(id)
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        RiskLevelDeletedEvent event = new RiskLevelDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

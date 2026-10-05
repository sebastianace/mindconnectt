package springboot.application.risklevel.usecase;

import springboot.application.risklevel.command.UpdateRiskLevelCommand;
import springboot.application.risklevel.dto.RiskLevelResponse;
import springboot.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.risklevel.model.aggregate.RiskLevel;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class UpdateRiskLevelUseCase {
    private final RiskLevelRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateRiskLevelUseCase(
            RiskLevelRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RiskLevelResponse execute(UpdateRiskLevelCommand command) {
        RiskLevel aggregate = repository.findById(command.id())
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active(),
                command.severity());
        RiskLevel saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return RiskLevelResponse.from(saved);
    }
}

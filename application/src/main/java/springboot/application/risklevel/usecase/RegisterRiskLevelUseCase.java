package springboot.application.risklevel.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.risklevel.command.RegisterRiskLevelCommand;
import springboot.application.risklevel.dto.RiskLevelResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.risklevel.model.aggregate.RiskLevel;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class RegisterRiskLevelUseCase {
    private final RiskLevelRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterRiskLevelUseCase(
            RiskLevelRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RiskLevelResponse execute(RegisterRiskLevelCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("RiskLevel", "code", command.code());
        }
        RiskLevel aggregate = RiskLevel.register(
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

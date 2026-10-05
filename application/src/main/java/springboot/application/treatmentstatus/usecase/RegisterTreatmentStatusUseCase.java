package springboot.application.treatmentstatus.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.treatmentstatus.command.RegisterTreatmentStatusCommand;
import springboot.application.treatmentstatus.dto.TreatmentStatusResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class RegisterTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterTreatmentStatusUseCase(
            TreatmentStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentStatusResponse execute(RegisterTreatmentStatusCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("TreatmentStatus", "code", command.code());
        }
        TreatmentStatus aggregate = TreatmentStatus.register(
                command.code(),
                command.name(),
                command.active());
        TreatmentStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentStatusResponse.from(saved);
    }
}

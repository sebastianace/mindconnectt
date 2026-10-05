package springboot.application.treatmentstatus.usecase;

import springboot.application.treatmentstatus.command.UpdateTreatmentStatusCommand;
import springboot.application.treatmentstatus.dto.TreatmentStatusResponse;
import springboot.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class UpdateTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateTreatmentStatusUseCase(
            TreatmentStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentStatusResponse execute(UpdateTreatmentStatusCommand command) {
        TreatmentStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        TreatmentStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentStatusResponse.from(saved);
    }
}

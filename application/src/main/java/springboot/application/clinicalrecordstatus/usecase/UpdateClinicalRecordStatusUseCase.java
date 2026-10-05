package springboot.application.clinicalrecordstatus.usecase;

import springboot.application.clinicalrecordstatus.command.UpdateClinicalRecordStatusCommand;
import springboot.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import springboot.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import springboot.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class UpdateClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateClinicalRecordStatusUseCase(
            ClinicalRecordStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalRecordStatusResponse execute(UpdateClinicalRecordStatusCommand command) {
        ClinicalRecordStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name());
        ClinicalRecordStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ClinicalRecordStatusResponse.from(saved);
    }
}

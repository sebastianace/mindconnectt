package springboot.application.clinicalrecordstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import springboot.domain.clinicalrecordstatus.event.ClinicalRecordStatusDeletedEvent;
import springboot.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import springboot.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalRecordStatusDeletedEvent execute(ClinicalRecordStatusId id) {
        ClinicalRecordStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ClinicalRecordStatusDeletedEvent event = new ClinicalRecordStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

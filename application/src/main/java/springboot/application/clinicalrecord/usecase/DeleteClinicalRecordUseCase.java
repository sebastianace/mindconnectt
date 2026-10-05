package springboot.application.clinicalrecord.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import springboot.domain.clinicalrecord.event.ClinicalRecordDeletedEvent;
import springboot.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import springboot.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteClinicalRecordUseCase {
    private final ClinicalRecordRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteClinicalRecordUseCase(ClinicalRecordRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalRecordDeletedEvent execute(ClinicalRecordId id) {
        ClinicalRecord aggregate = repository.findById(id)
                .orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ClinicalRecordDeletedEvent event = new ClinicalRecordDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

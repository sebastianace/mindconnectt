package springboot.application.patient.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.patient.exception.PatientNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.event.PatientDeletedEvent;
import springboot.domain.patient.model.aggregate.Patient;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.patient.port.repository.PatientRepository;

public class DeletePatientUseCase {
    private final PatientRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeletePatientUseCase(PatientRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PatientDeletedEvent execute(PatientId id) {
        Patient aggregate = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        PatientDeletedEvent event = new PatientDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

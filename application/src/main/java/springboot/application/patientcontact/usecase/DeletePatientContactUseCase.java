package springboot.application.patientcontact.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.patientcontact.exception.PatientContactNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patientcontact.event.PatientContactDeletedEvent;
import springboot.domain.patientcontact.model.aggregate.PatientContact;
import springboot.domain.patientcontact.model.valueobject.PatientContactId;
import springboot.domain.patientcontact.port.repository.PatientContactRepository;

public class DeletePatientContactUseCase {
    private final PatientContactRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeletePatientContactUseCase(PatientContactRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PatientContactDeletedEvent execute(PatientContactId id) {
        PatientContact aggregate = repository.findById(id)
                .orElseThrow(() -> new PatientContactNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        PatientContactDeletedEvent event = new PatientContactDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

package springboot.application.patientallergy.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patientallergy.event.PatientAllergyDeletedEvent;
import springboot.domain.patientallergy.model.aggregate.PatientAllergy;
import springboot.domain.patientallergy.model.valueobject.PatientAllergyId;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;

public class DeletePatientAllergyUseCase {
    private final PatientAllergyRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeletePatientAllergyUseCase(PatientAllergyRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public PatientAllergyDeletedEvent execute(PatientAllergyId id) {
        PatientAllergy aggregate = repository.findById(id)
                .orElseThrow(() -> new PatientAllergyNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        PatientAllergyDeletedEvent event = new PatientAllergyDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

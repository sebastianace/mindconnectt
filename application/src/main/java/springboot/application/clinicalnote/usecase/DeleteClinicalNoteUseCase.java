package springboot.application.clinicalnote.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import springboot.domain.clinicalnote.event.ClinicalNoteDeletedEvent;
import springboot.domain.clinicalnote.model.aggregate.ClinicalNote;
import springboot.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import springboot.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteClinicalNoteUseCase(ClinicalNoteRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalNoteDeletedEvent execute(ClinicalNoteId id) {
        ClinicalNote aggregate = repository.findById(id)
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ClinicalNoteDeletedEvent event = new ClinicalNoteDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

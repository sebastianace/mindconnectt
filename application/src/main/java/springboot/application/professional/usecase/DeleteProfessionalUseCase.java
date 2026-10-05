package springboot.application.professional.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.professional.exception.ProfessionalNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professional.event.ProfessionalDeletedEvent;
import springboot.domain.professional.model.aggregate.Professional;
import springboot.domain.professional.model.valueobject.ProfessionalId;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class DeleteProfessionalUseCase {
    private final ProfessionalRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteProfessionalUseCase(ProfessionalRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalDeletedEvent execute(ProfessionalId id) {
        Professional aggregate = repository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ProfessionalDeletedEvent event = new ProfessionalDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

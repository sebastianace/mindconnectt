package springboot.application.professionaltype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professionaltype.event.ProfessionalTypeDeletedEvent;
import springboot.domain.professionaltype.model.aggregate.ProfessionalType;
import springboot.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

public class DeleteProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteProfessionalTypeUseCase(ProfessionalTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalTypeDeletedEvent execute(ProfessionalTypeId id) {
        ProfessionalType aggregate = repository.findById(id)
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ProfessionalTypeDeletedEvent event = new ProfessionalTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

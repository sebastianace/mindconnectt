package springboot.application.gender.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.gender.exception.GenderNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.gender.event.GenderDeletedEvent;
import springboot.domain.gender.model.aggregate.Gender;
import springboot.domain.gender.model.valueobject.GenderId;
import springboot.domain.gender.port.repository.GenderRepository;

public class DeleteGenderUseCase {
    private final GenderRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteGenderUseCase(GenderRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public GenderDeletedEvent execute(GenderId id) {
        Gender aggregate = repository.findById(id)
                .orElseThrow(() -> new GenderNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        GenderDeletedEvent event = new GenderDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

package springboot.application.relationshiptype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.relationshiptype.event.RelationshipTypeDeletedEvent;
import springboot.domain.relationshiptype.model.aggregate.RelationshipType;
import springboot.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;

public class DeleteRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteRelationshipTypeUseCase(RelationshipTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RelationshipTypeDeletedEvent execute(RelationshipTypeId id) {
        RelationshipType aggregate = repository.findById(id)
                .orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        RelationshipTypeDeletedEvent event = new RelationshipTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}

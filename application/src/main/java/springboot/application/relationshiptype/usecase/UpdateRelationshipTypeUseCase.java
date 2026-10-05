package springboot.application.relationshiptype.usecase;

import springboot.application.relationshiptype.command.UpdateRelationshipTypeCommand;
import springboot.application.relationshiptype.dto.RelationshipTypeResponse;
import springboot.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.relationshiptype.model.aggregate.RelationshipType;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;

public class UpdateRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateRelationshipTypeUseCase(
            RelationshipTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RelationshipTypeResponse execute(UpdateRelationshipTypeCommand command) {
        RelationshipType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.description());
        RelationshipType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return RelationshipTypeResponse.from(saved);
    }
}

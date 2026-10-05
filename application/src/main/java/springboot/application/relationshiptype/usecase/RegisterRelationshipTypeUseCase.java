package springboot.application.relationshiptype.usecase;

import springboot.application.relationshiptype.command.RegisterRelationshipTypeCommand;
import springboot.application.relationshiptype.dto.RelationshipTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.relationshiptype.model.aggregate.RelationshipType;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;

public class RegisterRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterRelationshipTypeUseCase(
            RelationshipTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public RelationshipTypeResponse execute(RegisterRelationshipTypeCommand command) {
        RelationshipType aggregate = RelationshipType.register(
                command.description());
        RelationshipType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return RelationshipTypeResponse.from(saved);
    }
}

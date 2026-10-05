package springboot.application.relationshiptype.dto;

import java.util.UUID;

import springboot.domain.relationshiptype.model.aggregate.RelationshipType;

public record RelationshipTypeResponse(
        UUID id,
        String description
) {
    public static RelationshipTypeResponse from(RelationshipType aggregate) {
        return new RelationshipTypeResponse(
                aggregate.id().value(),
                aggregate.description());
    }
}

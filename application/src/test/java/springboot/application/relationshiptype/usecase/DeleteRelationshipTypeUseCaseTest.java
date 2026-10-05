package springboot.application.relationshiptype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.relationshiptype.event.RelationshipTypeDeletedEvent;
import springboot.domain.relationshiptype.model.aggregate.RelationshipType;
import springboot.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;

class DeleteRelationshipTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        RelationshipType aggregate = RelationshipType.register(
                "Parent");
        FakeRepository repository = new FakeRepository(aggregate);
        RelationshipTypeDeletedEvent event = new DeleteRelationshipTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(RelationshipTypeNotFoundApplicationException.class,
                () -> new DeleteRelationshipTypeUseCase(repository, published::addAll).execute(RelationshipTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements RelationshipTypeRepository {
        private final RelationshipType aggregate; private RelationshipType deletedAggregate;
        private FakeRepository(RelationshipType aggregate) { this.aggregate = aggregate; }
        @Override public RelationshipType save(RelationshipType value) { return value; }
        @Override public Optional<RelationshipType> findById(RelationshipTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<RelationshipType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(RelationshipTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(RelationshipType value) { deletedAggregate = value; }
        private RelationshipType deletedAggregate() { return deletedAggregate; }
    }
}

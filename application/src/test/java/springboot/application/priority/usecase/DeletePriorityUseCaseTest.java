package springboot.application.priority.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.priority.exception.PriorityNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.priority.event.PriorityDeletedEvent;
import springboot.domain.priority.model.aggregate.Priority;
import springboot.domain.priority.model.valueobject.PriorityId;
import springboot.domain.priority.port.repository.PriorityRepository;

class DeletePriorityUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        Priority aggregate = Priority.register(
                "High");
        FakeRepository repository = new FakeRepository(aggregate);
        PriorityDeletedEvent event = new DeletePriorityUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(PriorityNotFoundApplicationException.class,
                () -> new DeletePriorityUseCase(repository, published::addAll).execute(PriorityId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements PriorityRepository {
        private final Priority aggregate; private Priority deletedAggregate;
        private FakeRepository(Priority aggregate) { this.aggregate = aggregate; }
        @Override public Priority save(Priority value) { return value; }
        @Override public Optional<Priority> findById(PriorityId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<Priority> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(PriorityId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(Priority value) { deletedAggregate = value; }
        private Priority deletedAggregate() { return deletedAggregate; }
    }
}

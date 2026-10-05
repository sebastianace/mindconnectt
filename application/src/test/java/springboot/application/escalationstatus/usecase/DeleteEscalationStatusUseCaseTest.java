package springboot.application.escalationstatus.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.escalationstatus.event.EscalationStatusDeletedEvent;
import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;
import springboot.domain.escalationstatus.model.valueobject.EscalationStatusId;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

class DeleteEscalationStatusUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        EscalationStatus aggregate = EscalationStatus.register(
                "Open");
        FakeRepository repository = new FakeRepository(aggregate);
        EscalationStatusDeletedEvent event = new DeleteEscalationStatusUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(EscalationStatusNotFoundApplicationException.class,
                () -> new DeleteEscalationStatusUseCase(repository, published::addAll).execute(EscalationStatusId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements EscalationStatusRepository {
        private final EscalationStatus aggregate; private EscalationStatus deletedAggregate;
        private FakeRepository(EscalationStatus aggregate) { this.aggregate = aggregate; }
        @Override public EscalationStatus save(EscalationStatus value) { return value; }
        @Override public Optional<EscalationStatus> findById(EscalationStatusId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<EscalationStatus> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(EscalationStatusId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(EscalationStatus value) { deletedAggregate = value; }
        private EscalationStatus deletedAggregate() { return deletedAggregate; }
    }
}

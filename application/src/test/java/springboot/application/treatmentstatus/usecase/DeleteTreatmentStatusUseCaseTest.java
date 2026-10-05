package springboot.application.treatmentstatus.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.treatmentstatus.event.TreatmentStatusDeletedEvent;
import springboot.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import springboot.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

class DeleteTreatmentStatusUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        TreatmentStatus aggregate = TreatmentStatus.register(
                "ACTIVE",
                "Active",
                true);
        FakeRepository repository = new FakeRepository(aggregate);
        TreatmentStatusDeletedEvent event = new DeleteTreatmentStatusUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(TreatmentStatusNotFoundApplicationException.class,
                () -> new DeleteTreatmentStatusUseCase(repository, published::addAll).execute(TreatmentStatusId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements TreatmentStatusRepository {
        private final TreatmentStatus aggregate; private TreatmentStatus deletedAggregate;
        private FakeRepository(TreatmentStatus aggregate) { this.aggregate = aggregate; }
        @Override public TreatmentStatus save(TreatmentStatus value) { return value; }
        @Override public Optional<TreatmentStatus> findById(TreatmentStatusId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<TreatmentStatus> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(TreatmentStatusId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(TreatmentStatus value) { deletedAggregate = value; }
        private TreatmentStatus deletedAggregate() { return deletedAggregate; }
    }
}

package springboot.application.treatmentgoalstatus.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.treatmentgoalstatus.event.TreatmentGoalStatusDeletedEvent;
import springboot.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import springboot.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

class DeleteTreatmentGoalStatusUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        TreatmentGoalStatus aggregate = TreatmentGoalStatus.register(
                "IN_PROGRESS",
                "In progress",
                true);
        FakeRepository repository = new FakeRepository(aggregate);
        TreatmentGoalStatusDeletedEvent event = new DeleteTreatmentGoalStatusUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(TreatmentGoalStatusNotFoundApplicationException.class,
                () -> new DeleteTreatmentGoalStatusUseCase(repository, published::addAll).execute(TreatmentGoalStatusId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements TreatmentGoalStatusRepository {
        private final TreatmentGoalStatus aggregate; private TreatmentGoalStatus deletedAggregate;
        private FakeRepository(TreatmentGoalStatus aggregate) { this.aggregate = aggregate; }
        @Override public TreatmentGoalStatus save(TreatmentGoalStatus value) { return value; }
        @Override public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<TreatmentGoalStatus> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(TreatmentGoalStatusId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(TreatmentGoalStatus value) { deletedAggregate = value; }
        private TreatmentGoalStatus deletedAggregate() { return deletedAggregate; }
    }
}

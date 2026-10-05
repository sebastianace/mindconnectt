package springboot.application.risklevel.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.risklevel.event.RiskLevelDeletedEvent;
import springboot.domain.risklevel.model.aggregate.RiskLevel;
import springboot.domain.risklevel.model.valueobject.RiskLevelId;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

class DeleteRiskLevelUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        RiskLevel aggregate = RiskLevel.register(
                "HIGH",
                "High",
                true,
                3);
        FakeRepository repository = new FakeRepository(aggregate);
        RiskLevelDeletedEvent event = new DeleteRiskLevelUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(RiskLevelNotFoundApplicationException.class,
                () -> new DeleteRiskLevelUseCase(repository, published::addAll).execute(RiskLevelId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements RiskLevelRepository {
        private final RiskLevel aggregate; private RiskLevel deletedAggregate;
        private FakeRepository(RiskLevel aggregate) { this.aggregate = aggregate; }
        @Override public RiskLevel save(RiskLevel value) { return value; }
        @Override public Optional<RiskLevel> findById(RiskLevelId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<RiskLevel> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(RiskLevelId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(RiskLevel value) { deletedAggregate = value; }
        private RiskLevel deletedAggregate() { return deletedAggregate; }
    }
}

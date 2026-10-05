package springboot.application.airunstatus.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import springboot.domain.airunstatus.event.AiRunStatusDeletedEvent;
import springboot.domain.airunstatus.model.aggregate.AiRunStatus;
import springboot.domain.airunstatus.model.valueobject.AiRunStatusId;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.common.event.DomainEvent;

class DeleteAiRunStatusUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        AiRunStatus aggregate = AiRunStatus.register(
                "Completed");
        FakeRepository repository = new FakeRepository(aggregate);
        AiRunStatusDeletedEvent event = new DeleteAiRunStatusUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(AiRunStatusNotFoundApplicationException.class,
                () -> new DeleteAiRunStatusUseCase(repository, published::addAll).execute(AiRunStatusId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements AiRunStatusRepository {
        private final AiRunStatus aggregate; private AiRunStatus deletedAggregate;
        private FakeRepository(AiRunStatus aggregate) { this.aggregate = aggregate; }
        @Override public AiRunStatus save(AiRunStatus value) { return value; }
        @Override public Optional<AiRunStatus> findById(AiRunStatusId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<AiRunStatus> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(AiRunStatusId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(AiRunStatus value) { deletedAggregate = value; }
        private AiRunStatus deletedAggregate() { return deletedAggregate; }
    }
}

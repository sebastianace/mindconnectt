package springboot.application.conversationstatus.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.conversationstatus.event.ConversationStatusDeletedEvent;
import springboot.domain.conversationstatus.model.aggregate.ConversationStatus;
import springboot.domain.conversationstatus.model.valueobject.ConversationStatusId;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;

class DeleteConversationStatusUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ConversationStatus aggregate = ConversationStatus.register(
                "Open");
        FakeRepository repository = new FakeRepository(aggregate);
        ConversationStatusDeletedEvent event = new DeleteConversationStatusUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ConversationStatusNotFoundApplicationException.class,
                () -> new DeleteConversationStatusUseCase(repository, published::addAll).execute(ConversationStatusId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ConversationStatusRepository {
        private final ConversationStatus aggregate; private ConversationStatus deletedAggregate;
        private FakeRepository(ConversationStatus aggregate) { this.aggregate = aggregate; }
        @Override public ConversationStatus save(ConversationStatus value) { return value; }
        @Override public Optional<ConversationStatus> findById(ConversationStatusId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ConversationStatus> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ConversationStatusId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ConversationStatus value) { deletedAggregate = value; }
        private ConversationStatus deletedAggregate() { return deletedAggregate; }
    }
}

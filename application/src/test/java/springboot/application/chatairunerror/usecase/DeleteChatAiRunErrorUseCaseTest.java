package springboot.application.chatairunerror.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.chatairunerror.event.ChatAiRunErrorDeletedEvent;
import springboot.domain.chatairunerror.model.aggregate.ChatAiRunError;
import springboot.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import springboot.domain.common.event.DomainEvent;

class DeleteChatAiRunErrorUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ChatAiRunError aggregate = ChatAiRunError.register(
                ChatAiRunId.generate(),
                "Provider request failed",
                "PROVIDER_ERROR",
                "provider-error-001");
        FakeRepository repository = new FakeRepository(aggregate);
        ChatAiRunErrorDeletedEvent event = new DeleteChatAiRunErrorUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class,
                () -> new DeleteChatAiRunErrorUseCase(repository, published::addAll).execute(ChatAiRunErrorId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ChatAiRunErrorRepository {
        private final ChatAiRunError aggregate; private ChatAiRunError deletedAggregate;
        private FakeRepository(ChatAiRunError aggregate) { this.aggregate = aggregate; }
        @Override public ChatAiRunError save(ChatAiRunError value) { return value; }
        @Override public Optional<ChatAiRunError> findById(ChatAiRunErrorId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ChatAiRunError> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ChatAiRunErrorId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ChatAiRunError value) { deletedAggregate = value; }
        private ChatAiRunError deletedAggregate() { return deletedAggregate; }
    }
}

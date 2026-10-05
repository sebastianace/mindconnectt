package springboot.application.chatparticipant.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import springboot.domain.chatconversation.model.valueobject.ChatConversationId;
import springboot.domain.chatparticipant.event.ChatParticipantDeletedEvent;
import springboot.domain.chatparticipant.model.aggregate.ChatParticipant;
import springboot.domain.chatparticipant.model.valueobject.ChatParticipantId;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.sendertype.model.valueobject.SenderTypeId;

class DeleteChatParticipantUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ChatParticipant aggregate = ChatParticipant.register(
                ChatConversationId.generate(),
                SenderTypeId.generate(),
                null,
                null);
        FakeRepository repository = new FakeRepository(aggregate);
        ChatParticipantDeletedEvent event = new DeleteChatParticipantUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ChatParticipantNotFoundApplicationException.class,
                () -> new DeleteChatParticipantUseCase(repository, published::addAll).execute(ChatParticipantId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ChatParticipantRepository {
        private final ChatParticipant aggregate; private ChatParticipant deletedAggregate;
        private FakeRepository(ChatParticipant aggregate) { this.aggregate = aggregate; }
        @Override public ChatParticipant save(ChatParticipant value) { return value; }
        @Override public Optional<ChatParticipant> findById(ChatParticipantId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ChatParticipant> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ChatParticipantId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ChatParticipant value) { deletedAggregate = value; }
        private ChatParticipant deletedAggregate() { return deletedAggregate; }
    }
}

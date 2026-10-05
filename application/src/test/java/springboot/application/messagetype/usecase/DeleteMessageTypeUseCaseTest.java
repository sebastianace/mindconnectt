package springboot.application.messagetype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.messagetype.event.MessageTypeDeletedEvent;
import springboot.domain.messagetype.model.aggregate.MessageType;
import springboot.domain.messagetype.model.valueobject.MessageTypeId;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;

class DeleteMessageTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        MessageType aggregate = MessageType.register(
                "Text");
        FakeRepository repository = new FakeRepository(aggregate);
        MessageTypeDeletedEvent event = new DeleteMessageTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(MessageTypeNotFoundApplicationException.class,
                () -> new DeleteMessageTypeUseCase(repository, published::addAll).execute(MessageTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements MessageTypeRepository {
        private final MessageType aggregate; private MessageType deletedAggregate;
        private FakeRepository(MessageType aggregate) { this.aggregate = aggregate; }
        @Override public MessageType save(MessageType value) { return value; }
        @Override public Optional<MessageType> findById(MessageTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<MessageType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(MessageTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(MessageType value) { deletedAggregate = value; }
        private MessageType deletedAggregate() { return deletedAggregate; }
    }
}

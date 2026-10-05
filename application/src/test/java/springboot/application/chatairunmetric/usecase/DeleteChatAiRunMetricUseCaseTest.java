package springboot.application.chatairunmetric.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.chatairunmetric.event.ChatAiRunMetricDeletedEvent;
import springboot.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import springboot.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import springboot.domain.common.event.DomainEvent;

class DeleteChatAiRunMetricUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ChatAiRunMetric aggregate = ChatAiRunMetric.register(
                ChatAiRunId.generate(),
                100,
                50,
                150,
                new java.math.BigDecimal("0.001500"));
        FakeRepository repository = new FakeRepository(aggregate);
        ChatAiRunMetricDeletedEvent event = new DeleteChatAiRunMetricUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ChatAiRunMetricNotFoundApplicationException.class,
                () -> new DeleteChatAiRunMetricUseCase(repository, published::addAll).execute(ChatAiRunMetricId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ChatAiRunMetricRepository {
        private final ChatAiRunMetric aggregate; private ChatAiRunMetric deletedAggregate;
        private FakeRepository(ChatAiRunMetric aggregate) { this.aggregate = aggregate; }
        @Override public ChatAiRunMetric save(ChatAiRunMetric value) { return value; }
        @Override public Optional<ChatAiRunMetric> findById(ChatAiRunMetricId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ChatAiRunMetric> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ChatAiRunMetricId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ChatAiRunMetric value) { deletedAggregate = value; }
        private ChatAiRunMetric deletedAggregate() { return deletedAggregate; }
    }
}

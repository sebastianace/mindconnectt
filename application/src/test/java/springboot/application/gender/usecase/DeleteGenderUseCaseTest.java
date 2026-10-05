package springboot.application.gender.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.gender.exception.GenderNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.gender.event.GenderDeletedEvent;
import springboot.domain.gender.model.aggregate.Gender;
import springboot.domain.gender.model.valueobject.GenderId;
import springboot.domain.gender.port.repository.GenderRepository;

class DeleteGenderUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        Gender aggregate = Gender.register(
                "Female");
        FakeRepository repository = new FakeRepository(aggregate);
        GenderDeletedEvent event = new DeleteGenderUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(GenderNotFoundApplicationException.class,
                () -> new DeleteGenderUseCase(repository, published::addAll).execute(GenderId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements GenderRepository {
        private final Gender aggregate; private Gender deletedAggregate;
        private FakeRepository(Gender aggregate) { this.aggregate = aggregate; }
        @Override public Gender save(Gender value) { return value; }
        @Override public Optional<Gender> findById(GenderId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<Gender> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(GenderId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(Gender value) { deletedAggregate = value; }
        private Gender deletedAggregate() { return deletedAggregate; }
    }
}

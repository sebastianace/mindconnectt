package springboot.application.consenttype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.consenttype.event.ConsentTypeDeletedEvent;
import springboot.domain.consenttype.model.aggregate.ConsentType;
import springboot.domain.consenttype.model.valueobject.ConsentTypeId;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;

class DeleteConsentTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ConsentType aggregate = ConsentType.register(
                "TREATMENT",
                "Treatment consent",
                true,
                "Consent for treatment");
        FakeRepository repository = new FakeRepository(aggregate);
        ConsentTypeDeletedEvent event = new DeleteConsentTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ConsentTypeNotFoundApplicationException.class,
                () -> new DeleteConsentTypeUseCase(repository, published::addAll).execute(ConsentTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ConsentTypeRepository {
        private final ConsentType aggregate; private ConsentType deletedAggregate;
        private FakeRepository(ConsentType aggregate) { this.aggregate = aggregate; }
        @Override public ConsentType save(ConsentType value) { return value; }
        @Override public Optional<ConsentType> findById(ConsentTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ConsentType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(ConsentTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ConsentType value) { deletedAggregate = value; }
        private ConsentType deletedAggregate() { return deletedAggregate; }
    }
}

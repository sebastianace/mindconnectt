package springboot.application.phonecontact.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.phonecontact.exception.PhoneContactNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.contact.model.valueobject.ContactId;
import springboot.domain.phonecontact.event.PhoneContactDeletedEvent;
import springboot.domain.phonecontact.model.aggregate.PhoneContact;
import springboot.domain.phonecontact.model.valueobject.PhoneContactId;
import springboot.domain.phonecontact.port.repository.PhoneContactRepository;

class DeletePhoneContactUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        PhoneContact aggregate = PhoneContact.register(
                ContactId.generate(),
                null,
                "Call after 5 PM");
        FakeRepository repository = new FakeRepository(aggregate);
        PhoneContactDeletedEvent event = new DeletePhoneContactUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(PhoneContactNotFoundApplicationException.class,
                () -> new DeletePhoneContactUseCase(repository, published::addAll).execute(PhoneContactId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements PhoneContactRepository {
        private final PhoneContact aggregate; private PhoneContact deletedAggregate;
        private FakeRepository(PhoneContact aggregate) { this.aggregate = aggregate; }
        @Override public PhoneContact save(PhoneContact value) { return value; }
        @Override public Optional<PhoneContact> findById(PhoneContactId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<PhoneContact> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(PhoneContactId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(PhoneContact value) { deletedAggregate = value; }
        private PhoneContact deletedAggregate() { return deletedAggregate; }
    }
}

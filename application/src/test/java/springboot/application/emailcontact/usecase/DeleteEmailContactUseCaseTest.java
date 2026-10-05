package springboot.application.emailcontact.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.contact.model.valueobject.ContactId;
import springboot.domain.emailcontact.event.EmailContactDeletedEvent;
import springboot.domain.emailcontact.model.aggregate.EmailContact;
import springboot.domain.emailcontact.model.valueobject.EmailContactId;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;

class DeleteEmailContactUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        EmailContact aggregate = EmailContact.register(
                ContactId.generate(),
                "emergency@example.com",
                "Preferred email");
        FakeRepository repository = new FakeRepository(aggregate);
        EmailContactDeletedEvent event = new DeleteEmailContactUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(EmailContactNotFoundApplicationException.class,
                () -> new DeleteEmailContactUseCase(repository, published::addAll).execute(EmailContactId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements EmailContactRepository {
        private final EmailContact aggregate; private EmailContact deletedAggregate;
        private FakeRepository(EmailContact aggregate) { this.aggregate = aggregate; }
        @Override public EmailContact save(EmailContact value) { return value; }
        @Override public Optional<EmailContact> findById(EmailContactId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<EmailContact> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(EmailContactId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(EmailContact value) { deletedAggregate = value; }
        private EmailContact deletedAggregate() { return deletedAggregate; }
    }
}

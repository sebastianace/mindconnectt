package springboot.application.documenttype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.documenttype.event.DocumentTypeDeletedEvent;
import springboot.domain.documenttype.model.aggregate.DocumentType;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;

class DeleteDocumentTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        DocumentType aggregate = DocumentType.register(
                "CC",
                "Citizenship card",
                true);
        FakeRepository repository = new FakeRepository(aggregate);
        DocumentTypeDeletedEvent event = new DeleteDocumentTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(DocumentTypeNotFoundApplicationException.class,
                () -> new DeleteDocumentTypeUseCase(repository, published::addAll).execute(DocumentTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements DocumentTypeRepository {
        private final DocumentType aggregate; private DocumentType deletedAggregate;
        private FakeRepository(DocumentType aggregate) { this.aggregate = aggregate; }
        @Override public DocumentType save(DocumentType value) { return value; }
        @Override public Optional<DocumentType> findById(DocumentTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<DocumentType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(DocumentTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(DocumentType value) { deletedAggregate = value; }
        private DocumentType deletedAggregate() { return deletedAggregate; }
    }
}

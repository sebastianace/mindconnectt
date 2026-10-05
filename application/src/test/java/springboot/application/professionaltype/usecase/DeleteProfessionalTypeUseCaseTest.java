package springboot.application.professionaltype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.professionaltype.event.ProfessionalTypeDeletedEvent;
import springboot.domain.professionaltype.model.aggregate.ProfessionalType;
import springboot.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

class DeleteProfessionalTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ProfessionalType aggregate = ProfessionalType.register(
                "Psychologist");
        FakeRepository repository = new FakeRepository(aggregate);
        ProfessionalTypeDeletedEvent event = new DeleteProfessionalTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ProfessionalTypeNotFoundApplicationException.class,
                () -> new DeleteProfessionalTypeUseCase(repository, published::addAll).execute(ProfessionalTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ProfessionalTypeRepository {
        private final ProfessionalType aggregate; private ProfessionalType deletedAggregate;
        private FakeRepository(ProfessionalType aggregate) { this.aggregate = aggregate; }
        @Override public ProfessionalType save(ProfessionalType value) { return value; }
        @Override public Optional<ProfessionalType> findById(ProfessionalTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ProfessionalType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ProfessionalTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ProfessionalType value) { deletedAggregate = value; }
        private ProfessionalType deletedAggregate() { return deletedAggregate; }
    }
}

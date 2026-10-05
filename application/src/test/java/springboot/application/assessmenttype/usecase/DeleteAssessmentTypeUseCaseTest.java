package springboot.application.assessmenttype.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import springboot.domain.assessmenttype.event.AssessmentTypeDeletedEvent;
import springboot.domain.assessmenttype.model.aggregate.AssessmentType;
import springboot.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import springboot.domain.common.event.DomainEvent;

class DeleteAssessmentTypeUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        AssessmentType aggregate = AssessmentType.register(
                "INITIAL",
                "Initial assessment",
                true,
                "Initial clinical assessment");
        FakeRepository repository = new FakeRepository(aggregate);
        AssessmentTypeDeletedEvent event = new DeleteAssessmentTypeUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(AssessmentTypeNotFoundApplicationException.class,
                () -> new DeleteAssessmentTypeUseCase(repository, published::addAll).execute(AssessmentTypeId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements AssessmentTypeRepository {
        private final AssessmentType aggregate; private AssessmentType deletedAggregate;
        private FakeRepository(AssessmentType aggregate) { this.aggregate = aggregate; }
        @Override public AssessmentType save(AssessmentType value) { return value; }
        @Override public Optional<AssessmentType> findById(AssessmentTypeId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<AssessmentType> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public boolean existsById(AssessmentTypeId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(AssessmentType value) { deletedAggregate = value; }
        private AssessmentType deletedAggregate() { return deletedAggregate; }
    }
}

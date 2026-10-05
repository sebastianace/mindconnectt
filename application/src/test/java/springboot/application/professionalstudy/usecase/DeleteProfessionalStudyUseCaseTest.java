package springboot.application.professionalstudy.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.professional.model.valueobject.ProfessionalId;
import springboot.domain.professionalstudy.event.ProfessionalStudyDeletedEvent;
import springboot.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import springboot.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import springboot.domain.study.model.valueobject.StudyId;

class DeleteProfessionalStudyUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ProfessionalStudy aggregate = ProfessionalStudy.register(
                StudyId.generate(),
                ProfessionalId.generate(),
                "Clinical Psychology",
                "National University",
                true,
                null,
                CountryId.generate());
        FakeRepository repository = new FakeRepository(aggregate);
        ProfessionalStudyDeletedEvent event = new DeleteProfessionalStudyUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ProfessionalStudyNotFoundApplicationException.class,
                () -> new DeleteProfessionalStudyUseCase(repository, published::addAll).execute(ProfessionalStudyId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ProfessionalStudyRepository {
        private final ProfessionalStudy aggregate; private ProfessionalStudy deletedAggregate;
        private FakeRepository(ProfessionalStudy aggregate) { this.aggregate = aggregate; }
        @Override public ProfessionalStudy save(ProfessionalStudy value) { return value; }
        @Override public Optional<ProfessionalStudy> findById(ProfessionalStudyId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ProfessionalStudy> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ProfessionalStudyId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ProfessionalStudy value) { deletedAggregate = value; }
        private ProfessionalStudy deletedAggregate() { return deletedAggregate; }
    }
}

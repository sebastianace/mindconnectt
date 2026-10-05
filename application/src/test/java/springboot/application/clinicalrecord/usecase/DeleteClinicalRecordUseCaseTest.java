package springboot.application.clinicalrecord.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import springboot.domain.clinicalrecord.event.ClinicalRecordDeletedEvent;
import springboot.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import springboot.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.professional.model.valueobject.ProfessionalId;

class DeleteClinicalRecordUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        ClinicalRecord aggregate = ClinicalRecord.register(
                PatientId.generate(),
                java.time.LocalDateTime.of(2026, 1, 10, 8, 0),
                "CR-2026-0001",
                java.time.LocalDateTime.of(2026, 1, 10, 8, 0),
                java.time.LocalDateTime.of(2026, 1, 10, 9, 0),
                ClinicalRecordStatusId.generate(),
                ProfessionalId.generate());
        FakeRepository repository = new FakeRepository(aggregate);
        ClinicalRecordDeletedEvent event = new DeleteClinicalRecordUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(ClinicalRecordNotFoundApplicationException.class,
                () -> new DeleteClinicalRecordUseCase(repository, published::addAll).execute(ClinicalRecordId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements ClinicalRecordRepository {
        private final ClinicalRecord aggregate; private ClinicalRecord deletedAggregate;
        private FakeRepository(ClinicalRecord aggregate) { this.aggregate = aggregate; }
        @Override public ClinicalRecord save(ClinicalRecord value) { return value; }
        @Override public Optional<ClinicalRecord> findById(ClinicalRecordId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<ClinicalRecord> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(ClinicalRecordId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(ClinicalRecord value) { deletedAggregate = value; }
        private ClinicalRecord deletedAggregate() { return deletedAggregate; }
    }
}

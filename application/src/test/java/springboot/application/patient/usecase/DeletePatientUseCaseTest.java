package springboot.application.patient.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.patient.exception.PatientNotFoundApplicationException;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.gender.model.valueobject.GenderId;
import springboot.domain.patient.event.PatientDeletedEvent;
import springboot.domain.patient.model.aggregate.Patient;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.patient.port.repository.PatientRepository;

class DeletePatientUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        Patient aggregate = Patient.register(
                DocumentTypeId.generate(),
                "987654321",
                "Laura",
                null,
                "Gomez",
                null,
                java.time.LocalDate.of(1990, 1, 1),
                GenderId.generate(),
                GenderId.generate(),
                "laura@example.com",
                "3001234567",
                "Main Street 123",
                true,
                null,
                null,
                CityMunicipalityId.generate());
        FakeRepository repository = new FakeRepository(aggregate);
        PatientDeletedEvent event = new DeletePatientUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(PatientNotFoundApplicationException.class,
                () -> new DeletePatientUseCase(repository, published::addAll).execute(PatientId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements PatientRepository {
        private final Patient aggregate; private Patient deletedAggregate;
        private FakeRepository(Patient aggregate) { this.aggregate = aggregate; }
        @Override public Patient save(Patient value) { return value; }
        @Override public Optional<Patient> findById(PatientId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<Patient> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(PatientId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(Patient value) { deletedAggregate = value; }
        private Patient deletedAggregate() { return deletedAggregate; }
    }
}

package springboot.application.patientallergy.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.patientallergy.event.PatientAllergyDeletedEvent;
import springboot.domain.patientallergy.model.aggregate.PatientAllergy;
import springboot.domain.patientallergy.model.valueobject.PatientAllergyId;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;
import springboot.domain.professional.model.valueobject.ProfessionalId;

class DeletePatientAllergyUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        PatientAllergy aggregate = PatientAllergy.register(
                PatientId.generate(),
                "Penicillin",
                null,
                "HIGH",
                true,
                java.time.LocalDateTime.of(2026, 1, 10, 8, 0),
                ProfessionalId.generate());
        FakeRepository repository = new FakeRepository(aggregate);
        PatientAllergyDeletedEvent event = new DeletePatientAllergyUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(PatientAllergyNotFoundApplicationException.class,
                () -> new DeletePatientAllergyUseCase(repository, published::addAll).execute(PatientAllergyId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements PatientAllergyRepository {
        private final PatientAllergy aggregate; private PatientAllergy deletedAggregate;
        private FakeRepository(PatientAllergy aggregate) { this.aggregate = aggregate; }
        @Override public PatientAllergy save(PatientAllergy value) { return value; }
        @Override public Optional<PatientAllergy> findById(PatientAllergyId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<PatientAllergy> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }

        @Override public boolean existsById(PatientAllergyId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(PatientAllergy value) { deletedAggregate = value; }
        private PatientAllergy deletedAggregate() { return deletedAggregate; }
    }
}

package springboot.domain.patient.model.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.gender.model.valueobject.GenderId;

class PatientInvariantsTest {
    @Test void shouldRejectBirthDateInTheFuture() {
        assertThrows(DomainValidationException.class,
                () -> register(LocalDate.now().plusDays(1), "laura@example.com"));
    }

    @Test void shouldRejectInvalidEmail() {
        assertThrows(DomainValidationException.class,
                () -> register(LocalDate.of(1990, 1, 1), "laura-at-example.com"));
    }

    private static Patient register(LocalDate birthDate, String email) {
        return Patient.register(
                DocumentTypeId.generate(),
                "987654321",
                "Laura",
                null,
                "Gomez",
                null,
                birthDate,
                GenderId.generate(),
                GenderId.generate(),
                email,
                "3001234567",
                "Main Street 123",
                true,
                null,
                null,
                CityMunicipalityId.generate());
    }
}

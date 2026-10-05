package springboot.domain.treatmentplan.model.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.encounter.model.valueobject.EncounterId;
import springboot.domain.professional.model.valueobject.ProfessionalId;
import springboot.domain.treatmentstatus.model.valueobject.TreatmentStatusId;

class TreatmentPlanInvariantsTest {
    @Test void shouldRejectEndDateBeforeStartDate() {
        DomainValidationException ex = assertThrows(DomainValidationException.class,
                () -> TreatmentPlan.register(
                        EncounterId.generate(),
                        ProfessionalId.generate(),
                        "Plan",
                        "Description",
                        LocalDate.of(2026, 6, 10),
                        LocalDate.of(2026, 1, 10),
                        TreatmentStatusId.generate()));
        assertEquals("endDate must not be before startDate", ex.getMessage());
    }

    @Test void shouldRejectBlankTitle() {
        assertThrows(DomainValidationException.class,
                () -> TreatmentPlan.register(
                        EncounterId.generate(),
                        ProfessionalId.generate(),
                        " ",
                        "Description",
                        LocalDate.of(2026, 1, 10),
                        LocalDate.of(2026, 6, 10),
                        TreatmentStatusId.generate()));
    }
}

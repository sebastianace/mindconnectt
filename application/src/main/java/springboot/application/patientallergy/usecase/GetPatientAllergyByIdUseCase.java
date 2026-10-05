package springboot.application.patientallergy.usecase;

import springboot.application.patientallergy.dto.PatientAllergyResponse;
import springboot.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import springboot.domain.patientallergy.model.valueobject.PatientAllergyId;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;

public class GetPatientAllergyByIdUseCase {
    private final PatientAllergyRepository repository;

    public GetPatientAllergyByIdUseCase(PatientAllergyRepository repository) {
        this.repository = repository;
    }

    public PatientAllergyResponse execute(PatientAllergyId id) {
        return repository.findById(id)
                .map(PatientAllergyResponse::from)
                .orElseThrow(() -> new PatientAllergyNotFoundApplicationException(id.value().toString()));
    }
}

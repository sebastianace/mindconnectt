package springboot.application.patientallergy.usecase;

import java.util.List;

import springboot.application.patientallergy.dto.PatientAllergyResponse;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;

public class ListPatientAllergyUseCase {
    private final PatientAllergyRepository repository;

    public ListPatientAllergyUseCase(PatientAllergyRepository repository) {
        this.repository = repository;
    }

    public List<PatientAllergyResponse> execute() {
        return repository.findAll().stream()
                .map(PatientAllergyResponse::from)
                .toList();
    }
}

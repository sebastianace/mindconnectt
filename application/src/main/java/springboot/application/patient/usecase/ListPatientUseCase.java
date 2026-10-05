package springboot.application.patient.usecase;

import java.util.List;

import springboot.application.patient.dto.PatientResponse;
import springboot.domain.patient.port.repository.PatientRepository;

public class ListPatientUseCase {
    private final PatientRepository repository;

    public ListPatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponse> execute() {
        return repository.findAll().stream()
                .map(PatientResponse::from)
                .toList();
    }
}

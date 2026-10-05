package springboot.application.patientcontact.usecase;

import java.util.List;

import springboot.application.patientcontact.dto.PatientContactResponse;
import springboot.domain.patientcontact.port.repository.PatientContactRepository;

public class ListPatientContactUseCase {
    private final PatientContactRepository repository;

    public ListPatientContactUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public List<PatientContactResponse> execute() {
        return repository.findAll().stream()
                .map(PatientContactResponse::from)
                .toList();
    }
}

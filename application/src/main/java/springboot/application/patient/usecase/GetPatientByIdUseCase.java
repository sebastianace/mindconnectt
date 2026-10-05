package springboot.application.patient.usecase;

import springboot.application.patient.dto.PatientResponse;
import springboot.application.patient.exception.PatientNotFoundApplicationException;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.patient.port.repository.PatientRepository;

public class GetPatientByIdUseCase {
    private final PatientRepository repository;

    public GetPatientByIdUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public PatientResponse execute(PatientId id) {
        return repository.findById(id)
                .map(PatientResponse::from)
                .orElseThrow(() -> new PatientNotFoundApplicationException(id.value().toString()));
    }
}

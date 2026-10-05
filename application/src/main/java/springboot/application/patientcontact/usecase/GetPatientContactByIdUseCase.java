package springboot.application.patientcontact.usecase;

import springboot.application.patientcontact.dto.PatientContactResponse;
import springboot.application.patientcontact.exception.PatientContactNotFoundApplicationException;
import springboot.domain.patientcontact.model.valueobject.PatientContactId;
import springboot.domain.patientcontact.port.repository.PatientContactRepository;

public class GetPatientContactByIdUseCase {
    private final PatientContactRepository repository;

    public GetPatientContactByIdUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public PatientContactResponse execute(PatientContactId id) {
        return repository.findById(id)
                .map(PatientContactResponse::from)
                .orElseThrow(() -> new PatientContactNotFoundApplicationException(id.value().toString()));
    }
}

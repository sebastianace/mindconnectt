package springboot.application.clinicalrecord.usecase;

import springboot.application.clinicalrecord.dto.ClinicalRecordResponse;
import springboot.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import springboot.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;

public class GetClinicalRecordByIdUseCase {
    private final ClinicalRecordRepository repository;

    public GetClinicalRecordByIdUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordResponse execute(ClinicalRecordId id) {
        return repository.findById(id)
                .map(ClinicalRecordResponse::from)
                .orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(id.value().toString()));
    }
}

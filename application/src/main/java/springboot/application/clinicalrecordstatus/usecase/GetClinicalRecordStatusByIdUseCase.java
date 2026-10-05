package springboot.application.clinicalrecordstatus.usecase;

import springboot.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import springboot.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import springboot.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class GetClinicalRecordStatusByIdUseCase {
    private final ClinicalRecordStatusRepository repository;

    public GetClinicalRecordStatusByIdUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordStatusResponse execute(ClinicalRecordStatusId id) {
        return repository.findById(id)
                .map(ClinicalRecordStatusResponse::from)
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(id.value().toString()));
    }
}

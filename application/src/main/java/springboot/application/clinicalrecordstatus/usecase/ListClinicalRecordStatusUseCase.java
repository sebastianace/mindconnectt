package springboot.application.clinicalrecordstatus.usecase;

import java.util.List;

import springboot.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class ListClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;

    public ListClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public List<ClinicalRecordStatusResponse> execute() {
        return repository.findAll().stream()
                .map(ClinicalRecordStatusResponse::from)
                .toList();
    }
}

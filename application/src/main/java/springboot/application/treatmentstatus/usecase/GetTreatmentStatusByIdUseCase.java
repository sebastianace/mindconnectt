package springboot.application.treatmentstatus.usecase;

import springboot.application.treatmentstatus.dto.TreatmentStatusResponse;
import springboot.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import springboot.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class GetTreatmentStatusByIdUseCase {
    private final TreatmentStatusRepository repository;

    public GetTreatmentStatusByIdUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentStatusResponse execute(TreatmentStatusId id) {
        return repository.findById(id)
                .map(TreatmentStatusResponse::from)
                .orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(id.value().toString()));
    }
}

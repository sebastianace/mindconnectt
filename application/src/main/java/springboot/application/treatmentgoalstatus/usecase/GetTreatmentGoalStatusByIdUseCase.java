package springboot.application.treatmentgoalstatus.usecase;

import springboot.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import springboot.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import springboot.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class GetTreatmentGoalStatusByIdUseCase {
    private final TreatmentGoalStatusRepository repository;

    public GetTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalStatusResponse execute(TreatmentGoalStatusId id) {
        return repository.findById(id)
                .map(TreatmentGoalStatusResponse::from)
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(id.value().toString()));
    }
}

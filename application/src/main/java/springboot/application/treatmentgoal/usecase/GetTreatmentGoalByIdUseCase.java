package springboot.application.treatmentgoal.usecase;

import springboot.application.treatmentgoal.dto.TreatmentGoalResponse;
import springboot.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import springboot.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;

public class GetTreatmentGoalByIdUseCase {
    private final TreatmentGoalRepository repository;

    public GetTreatmentGoalByIdUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public TreatmentGoalResponse execute(TreatmentGoalId id) {
        return repository.findById(id)
                .map(TreatmentGoalResponse::from)
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException(id.value().toString()));
    }
}

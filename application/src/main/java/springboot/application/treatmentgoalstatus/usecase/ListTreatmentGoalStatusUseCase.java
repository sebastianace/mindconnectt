package springboot.application.treatmentgoalstatus.usecase;

import java.util.List;

import springboot.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class ListTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;

    public ListTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentGoalStatusResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentGoalStatusResponse::from)
                .toList();
    }
}

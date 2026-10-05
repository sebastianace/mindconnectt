package springboot.application.treatmentgoal.usecase;

import java.util.List;

import springboot.application.treatmentgoal.dto.TreatmentGoalResponse;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;

public class ListTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;

    public ListTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentGoalResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentGoalResponse::from)
                .toList();
    }
}

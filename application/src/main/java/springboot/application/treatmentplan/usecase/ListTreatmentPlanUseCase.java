package springboot.application.treatmentplan.usecase;

import java.util.List;

import springboot.application.treatmentplan.dto.TreatmentPlanResponse;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;

public class ListTreatmentPlanUseCase {
    private final TreatmentPlanRepository repository;

    public ListTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentPlanResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentPlanResponse::from)
                .toList();
    }
}

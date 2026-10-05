package springboot.application.treatmentstatus.usecase;

import java.util.List;

import springboot.application.treatmentstatus.dto.TreatmentStatusResponse;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class ListTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;

    public ListTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentStatusResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentStatusResponse::from)
                .toList();
    }
}

package springboot.application.medicationroute.usecase;

import java.util.List;

import springboot.application.medicationroute.dto.MedicationRouteResponse;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;

public class ListMedicationRouteUseCase {
    private final MedicationRouteRepository repository;

    public ListMedicationRouteUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public List<MedicationRouteResponse> execute() {
        return repository.findAll().stream()
                .map(MedicationRouteResponse::from)
                .toList();
    }
}

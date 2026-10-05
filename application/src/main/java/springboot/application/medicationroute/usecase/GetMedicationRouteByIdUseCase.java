package springboot.application.medicationroute.usecase;

import springboot.application.medicationroute.dto.MedicationRouteResponse;
import springboot.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import springboot.domain.medicationroute.model.valueobject.MedicationRouteId;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;

public class GetMedicationRouteByIdUseCase {
    private final MedicationRouteRepository repository;

    public GetMedicationRouteByIdUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public MedicationRouteResponse execute(MedicationRouteId id) {
        return repository.findById(id)
                .map(MedicationRouteResponse::from)
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id.value().toString()));
    }
}

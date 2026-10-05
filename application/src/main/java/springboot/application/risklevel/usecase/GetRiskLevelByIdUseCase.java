package springboot.application.risklevel.usecase;

import springboot.application.risklevel.dto.RiskLevelResponse;
import springboot.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import springboot.domain.risklevel.model.valueobject.RiskLevelId;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class GetRiskLevelByIdUseCase {
    private final RiskLevelRepository repository;

    public GetRiskLevelByIdUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public RiskLevelResponse execute(RiskLevelId id) {
        return repository.findById(id)
                .map(RiskLevelResponse::from)
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(id.value().toString()));
    }
}

package springboot.application.risklevel.usecase;

import java.util.List;

import springboot.application.risklevel.dto.RiskLevelResponse;
import springboot.domain.risklevel.port.repository.RiskLevelRepository;

public class ListRiskLevelUseCase {
    private final RiskLevelRepository repository;

    public ListRiskLevelUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public List<RiskLevelResponse> execute() {
        return repository.findAll().stream()
                .map(RiskLevelResponse::from)
                .toList();
    }
}

package springboot.application.gender.usecase;

import java.util.List;

import springboot.application.gender.dto.GenderResponse;
import springboot.domain.gender.port.repository.GenderRepository;

public class ListGenderUseCase {
    private final GenderRepository repository;

    public ListGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public List<GenderResponse> execute() {
        return repository.findAll().stream()
                .map(GenderResponse::from)
                .toList();
    }
}

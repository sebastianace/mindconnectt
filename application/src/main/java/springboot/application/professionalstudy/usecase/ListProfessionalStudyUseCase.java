package springboot.application.professionalstudy.usecase;

import java.util.List;

import springboot.application.professionalstudy.dto.ProfessionalStudyResponse;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;

public class ListProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;

    public ListProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        this.repository = repository;
    }

    public List<ProfessionalStudyResponse> execute() {
        return repository.findAll().stream()
                .map(ProfessionalStudyResponse::from)
                .toList();
    }
}

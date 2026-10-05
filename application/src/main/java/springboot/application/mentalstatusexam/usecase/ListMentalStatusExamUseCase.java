package springboot.application.mentalstatusexam.usecase;

import java.util.List;

import springboot.application.mentalstatusexam.dto.MentalStatusExamResponse;
import springboot.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class ListMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;

    public ListMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public List<MentalStatusExamResponse> execute() {
        return repository.findAll().stream()
                .map(MentalStatusExamResponse::from)
                .toList();
    }
}

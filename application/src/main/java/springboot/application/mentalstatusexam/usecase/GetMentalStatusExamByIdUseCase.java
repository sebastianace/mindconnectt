package springboot.application.mentalstatusexam.usecase;

import springboot.application.mentalstatusexam.dto.MentalStatusExamResponse;
import springboot.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import springboot.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import springboot.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class GetMentalStatusExamByIdUseCase {
    private final MentalStatusExamRepository repository;

    public GetMentalStatusExamByIdUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public MentalStatusExamResponse execute(MentalStatusExamId id) {
        return repository.findById(id)
                .map(MentalStatusExamResponse::from)
                .orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(id.value().toString()));
    }
}

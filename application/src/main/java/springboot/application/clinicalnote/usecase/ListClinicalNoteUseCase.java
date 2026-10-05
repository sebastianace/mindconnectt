package springboot.application.clinicalnote.usecase;

import java.util.List;

import springboot.application.clinicalnote.dto.ClinicalNoteResponse;
import springboot.domain.clinicalnote.port.repository.ClinicalNoteRepository;

public class ListClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;

    public ListClinicalNoteUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public List<ClinicalNoteResponse> execute() {
        return repository.findAll().stream()
                .map(ClinicalNoteResponse::from)
                .toList();
    }
}

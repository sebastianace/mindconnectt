package springboot.application.clinicalnote.usecase;

import springboot.application.clinicalnote.dto.ClinicalNoteResponse;
import springboot.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import springboot.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import springboot.domain.clinicalnote.port.repository.ClinicalNoteRepository;

public class GetClinicalNoteByIdUseCase {
    private final ClinicalNoteRepository repository;

    public GetClinicalNoteByIdUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public ClinicalNoteResponse execute(ClinicalNoteId id) {
        return repository.findById(id)
                .map(ClinicalNoteResponse::from)
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(id.value().toString()));
    }
}

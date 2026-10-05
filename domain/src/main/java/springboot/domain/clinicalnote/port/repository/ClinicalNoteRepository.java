package springboot.domain.clinicalnote.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.clinicalnote.model.aggregate.ClinicalNote;
import springboot.domain.clinicalnote.model.valueobject.ClinicalNoteId;

public interface ClinicalNoteRepository {
    ClinicalNote save(ClinicalNote aggregate);
    Optional<ClinicalNote> findById(ClinicalNoteId id);
    List<ClinicalNote> findAll();
    boolean existsById(ClinicalNoteId id);
    void delete(ClinicalNote aggregate);
}

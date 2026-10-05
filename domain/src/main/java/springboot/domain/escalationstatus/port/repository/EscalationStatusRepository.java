package springboot.domain.escalationstatus.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.escalationstatus.model.aggregate.EscalationStatus;
import springboot.domain.escalationstatus.model.valueobject.EscalationStatusId;

public interface EscalationStatusRepository {
    EscalationStatus save(EscalationStatus aggregate);
    Optional<EscalationStatus> findById(EscalationStatusId id);
    List<EscalationStatus> findAll();
    boolean existsById(EscalationStatusId id);
    void delete(EscalationStatus aggregate);
}

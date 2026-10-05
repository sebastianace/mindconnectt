package springboot.domain.airunstatus.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.airunstatus.model.aggregate.AiRunStatus;
import springboot.domain.airunstatus.model.valueobject.AiRunStatusId;

public interface AiRunStatusRepository {
    AiRunStatus save(AiRunStatus aggregate);
    Optional<AiRunStatus> findById(AiRunStatusId id);
    List<AiRunStatus> findAll();
    boolean existsById(AiRunStatusId id);
    void delete(AiRunStatus aggregate);
}

package springboot.domain.treatmentplan.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.treatmentplan.model.aggregate.TreatmentPlan;
import springboot.domain.treatmentplan.model.valueobject.TreatmentPlanId;

public interface TreatmentPlanRepository {
    TreatmentPlan save(TreatmentPlan aggregate);
    Optional<TreatmentPlan> findById(TreatmentPlanId id);
    List<TreatmentPlan> findAll();
    boolean existsById(TreatmentPlanId id);
    void delete(TreatmentPlan aggregate);
}

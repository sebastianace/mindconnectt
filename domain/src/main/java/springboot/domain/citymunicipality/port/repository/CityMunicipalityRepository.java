package springboot.domain.citymunicipality.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.stateregion.model.valueobject.StateRegionId;

public interface CityMunicipalityRepository {
    CityMunicipality save(CityMunicipality aggregate);
    Optional<CityMunicipality> findById(CityMunicipalityId id);
    List<CityMunicipality> findAll();
    boolean existsById(CityMunicipalityId id);
    boolean existsByRegionIdAndCode(StateRegionId regionId, String code);
    void delete(CityMunicipality aggregate);
}

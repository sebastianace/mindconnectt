package springboot.infrastructure.citymunicipality.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.stateregion.model.valueobject.StateRegionId;
import springboot.infrastructure.citymunicipality.adapters.out.persistence.entity.CityMunicipalityJpaEntity;
import springboot.infrastructure.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;

public class CityMunicipalityRepositoryAdapter implements CityMunicipalityRepository {
    private final CityMunicipalityJpaRepository jpaRepository;
    private final CityMunicipalityPersistenceMapper mapper;
    public CityMunicipalityRepositoryAdapter(CityMunicipalityJpaRepository jpaRepository, CityMunicipalityPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository; this.mapper = mapper;
    }
    @Override public CityMunicipality save(CityMunicipality aggregate) {
        CityMunicipalityJpaEntity saved = jpaRepository.save(mapper.toJpa(aggregate)); return mapper.toDomain(saved);
    }
    @Override public Optional<CityMunicipality> findById(CityMunicipalityId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }
    @Override public List<CityMunicipality> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
    @Override public boolean existsByRegionIdAndCode(StateRegionId regionId, String code) {
        return jpaRepository.existsByRegionIdAndCodeCity(regionId.value(), code);
    }
    @Override public boolean existsById(CityMunicipalityId id) { return jpaRepository.existsById(id.value()); }
    @Override public void delete(CityMunicipality aggregate) { jpaRepository.deleteById(aggregate.id().value()); }
}

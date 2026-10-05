package springboot.infrastructure.stateregion.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.stateregion.model.aggregate.StateRegion;
import springboot.domain.stateregion.model.valueobject.StateRegionId;
import springboot.domain.stateregion.port.repository.StateRegionRepository;
import springboot.infrastructure.stateregion.adapters.out.persistence.entity.StateRegionJpaEntity;
import springboot.infrastructure.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;

public class StateRegionRepositoryAdapter implements StateRegionRepository {
    private final StateRegionJpaRepository jpaRepository;
    private final StateRegionPersistenceMapper mapper;
    public StateRegionRepositoryAdapter(StateRegionJpaRepository jpaRepository, StateRegionPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository; this.mapper = mapper;
    }
    @Override public StateRegion save(StateRegion aggregate) {
        StateRegionJpaEntity saved = jpaRepository.save(mapper.toJpa(aggregate)); return mapper.toDomain(saved);
    }
    @Override public Optional<StateRegion> findById(StateRegionId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }
    @Override public List<StateRegion> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
    @Override public boolean existsByCountryIdAndCode(CountryId countryId, String code) {
        return jpaRepository.existsByCountryIdAndCodeRegion(countryId.value(), code);
    }
    @Override public boolean existsById(StateRegionId id) { return jpaRepository.existsById(id.value()); }
    @Override public void delete(StateRegion aggregate) { jpaRepository.deleteById(aggregate.id().value()); }
}

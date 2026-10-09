package springboot.infrastructure.role.adapters.out.persistence.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.infrastructure.role.adapters.out.persistence.mappers.RolePersistenceMapper;

public class RoleRepositoryAdapter implements RoleRepository {
    private final RoleJpaRepository jpaRepository;
    private final RolePersistenceMapper mapper;

    public RoleRepositoryAdapter(RoleJpaRepository jpaRepository, RolePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override public List<Role> findAllById(Collection<RoleId> ids) {
        return jpaRepository.findAllById(ids.stream().map(RoleId::value).toList())
                .stream().map(mapper::toDomain).toList();
    }
}

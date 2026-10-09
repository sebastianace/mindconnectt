package springboot.domain.role.port.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    List<Role> findAllById(Collection<RoleId> ids);
}

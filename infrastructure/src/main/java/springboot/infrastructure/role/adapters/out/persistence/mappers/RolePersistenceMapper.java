package springboot.infrastructure.role.adapters.out.persistence.mappers;

import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.infrastructure.role.adapters.out.persistence.entity.RoleJpaEntity;

public class RolePersistenceMapper {
    public Role toDomain(RoleJpaEntity jpa) {
        if (jpa == null) { return null; }
        return Role.restore(new RoleId(jpa.getId()), jpa.getName(), jpa.getDescription(), jpa.getCreatedAt());
    }
}

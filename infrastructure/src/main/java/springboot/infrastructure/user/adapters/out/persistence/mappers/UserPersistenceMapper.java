package springboot.infrastructure.user.adapters.out.persistence.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.model.valueobject.UserId;
import springboot.infrastructure.user.adapters.out.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {
    public UserJpaEntity toJpa(User domain) {
        if (domain == null) { return null; }
        UserJpaEntity jpa = new UserJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setUsername(domain.username());
        jpa.setPassword(domain.passwordHash());
        jpa.setEnabled(domain.enabled());
        Set<UUID> roleIds = new HashSet<>();
        domain.roleIds().forEach(roleId -> roleIds.add(roleId.value()));
        jpa.setRoleIds(roleIds);
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public User toDomain(UserJpaEntity jpa) {
        if (jpa == null) { return null; }
        return User.restore(
                new UserId(jpa.getId()),
                jpa.getUsername(),
                jpa.getPassword(),
                jpa.isEnabled(),
                jpa.getRoleIds().stream().map(RoleId::new).collect(Collectors.toSet()),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}

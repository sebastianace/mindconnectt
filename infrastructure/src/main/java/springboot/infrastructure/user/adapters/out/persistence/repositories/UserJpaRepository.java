package springboot.infrastructure.user.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.infrastructure.user.adapters.out.persistence.entity.UserJpaEntity;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    boolean existsByUsername(String username);
    Optional<UserJpaEntity> findByUsername(String username);
}

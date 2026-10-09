package springboot.infrastructure.role.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.domain.role.port.repository.RoleRepository;
import springboot.infrastructure.role.adapters.out.persistence.mappers.RolePersistenceMapper;
import springboot.infrastructure.role.adapters.out.persistence.repositories.RoleJpaRepository;
import springboot.infrastructure.role.adapters.out.persistence.repositories.RoleRepositoryAdapter;

/** Ensambla el contexto Role: es un catálogo de solo lectura (se siembra con Flyway). */
@Configuration
public class RoleBeansConfig {

    @Bean
    public RolePersistenceMapper rolePersistenceMapper() {
        return new RolePersistenceMapper();
    }

    @Bean
    public RoleRepository roleRepository(RoleJpaRepository jpaRepository, RolePersistenceMapper mapper) {
        return new RoleRepositoryAdapter(jpaRepository, mapper);
    }
}

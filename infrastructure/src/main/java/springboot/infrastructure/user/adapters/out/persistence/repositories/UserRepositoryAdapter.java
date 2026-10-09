package springboot.infrastructure.user.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.model.valueobject.UserId;
import springboot.domain.user.port.repository.UserRepository;
import springboot.infrastructure.user.adapters.out.persistence.entity.UserJpaEntity;
import springboot.infrastructure.user.adapters.out.persistence.mappers.UserPersistenceMapper;

public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override public User save(User aggregate) {
        UserJpaEntity saved = jpaRepository.save(mapper.toJpa(aggregate));
        return mapper.toDomain(saved);
    }

    @Override public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override public List<User> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}

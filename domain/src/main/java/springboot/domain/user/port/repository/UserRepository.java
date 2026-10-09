package springboot.domain.user.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.model.valueobject.UserId;

public interface UserRepository {
    User save(User aggregate);
    Optional<User> findById(UserId id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    boolean existsByUsername(String username);
}

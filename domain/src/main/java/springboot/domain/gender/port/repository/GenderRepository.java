package springboot.domain.gender.port.repository;

import java.util.List;
import java.util.Optional;

import springboot.domain.gender.model.aggregate.Gender;
import springboot.domain.gender.model.valueobject.GenderId;

public interface GenderRepository {
    Gender save(Gender aggregate);
    Optional<Gender> findById(GenderId id);
    List<Gender> findAll();
    boolean existsById(GenderId id);
    void delete(Gender aggregate);
}

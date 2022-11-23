package com.mdweb.evisa.repository;

import com.mdweb.evisa.domain.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Person entity.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    @Query("{}")
    Page<Person> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Person> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Person> findOneWithEagerRelationships(String id);
}

package com.mdweb.evisa.repository;

import com.mdweb.evisa.domain.Request;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Request entity.
 */
@Repository
public interface RequestRepository extends MongoRepository<Request, String> {
    @Query("{}")
    Page<Request> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Request> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Request> findOneWithEagerRelationships(String id);
}

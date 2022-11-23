package com.mdweb.evisa.repository;

import com.mdweb.evisa.domain.Approval;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Approval entity.
 */
@Repository
public interface ApprovalRepository extends MongoRepository<Approval, String> {
    @Query("{}")
    Page<Approval> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Approval> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Approval> findOneWithEagerRelationships(String id);
}

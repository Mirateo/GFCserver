package gfc.backend.repository;

import gfc.backend.model.Relationship;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends CrudRepository<Relationship, Long> {
    Optional<Relationship> findByParent(User parent);
    Boolean existsByParent(User parent);

    Optional<Relationship> findByRelationId(Long relationId);
    Boolean existsByRelationId(Long relationId);

}


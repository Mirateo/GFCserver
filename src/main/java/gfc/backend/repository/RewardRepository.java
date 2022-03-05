package gfc.backend.repository;

import gfc.backend.model.Reward;
import gfc.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository  extends CrudRepository<Reward, Long> {

    Optional<Reward> findByRewardId(Long id);

    Boolean existsByRewardId(Long id);

    Iterable<Reward> findByOwner(User owner);

    Iterable<Reward> findByReporter(User reporter);

    Boolean existsByOwner(User owner);

    Boolean existsByReporter(User reporter);
}


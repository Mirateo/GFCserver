package gfc.backend.repository;

import gfc.backend.model.RepeatableTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepeatableTaskRepository extends CrudRepository<RepeatableTask, Long>  {
    List<RepeatableTask> findAllByOwnerId(Long ownerId);
}

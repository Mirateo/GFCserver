package gfc.backend.repository;

import gfc.backend.model.DoneTask;
import gfc.backend.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoneTasksRepository extends CrudRepository<DoneTask, Long> {
    List<DoneTask> findAllByOwnerId(Long id);
}

package gfc.backend.repository;

import gfc.backend.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByOwnerId(long ownerId);

}

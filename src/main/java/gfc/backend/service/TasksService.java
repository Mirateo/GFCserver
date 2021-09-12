package gfc.backend.service;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.Task;
import gfc.backend.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksService {
    TasksRepository tasksRepository;

    public List<Task> getAllUserTasks(long ownerId) {
        return tasksRepository.findAllByOwnerId(ownerId);
    }

    public Long addTask(TaskDTO newTask) {
        return tasksRepository.save(new Task(newTask)).getId();
    }

    public Long editTask(Task editedTask) {
        return tasksRepository.save(editedTask).getId();
    }

}

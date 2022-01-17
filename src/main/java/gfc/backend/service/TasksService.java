package gfc.backend.service;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.repository.RepeatableTaskRepository;
import gfc.backend.repository.TasksRepository;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksService {
    TasksRepository tasksRepository;
    RepeatableTaskRepository repeatableTaskRepository;

    public List<Task> getAllUserTasks(Long ownerId) {
        return tasksRepository.findAllByOwnerId(ownerId);
    }

    public List<RepeatableTask> getAllUserRepeatableTasks(Long ownerId) {
        return repeatableTaskRepository.findAllByOwnerId(ownerId);
    }

    public Long addTask(TaskDTO newTask) {
        System.out.println(newTask.toString());
        if ( newTask.getRepeatable() )
            return repeatableTaskRepository.save(new RepeatableTask(newTask)).getId();
        else
            return tasksRepository.save(new Task(newTask)).getId();
    }

    public Long editTask(Task editedTask) {
        if ( tasksRepository.findById(editedTask.getId()).isPresent())
            return tasksRepository.save(editedTask).getId();

        if ( repeatableTaskRepository.findById(editedTask.getId()).isPresent() ) {
            repeatableTaskRepository.deleteById(editedTask.getId());
            return tasksRepository.save(editedTask).getId();
        }

        return -1L;
    }

    public Long editReTask(RepeatableTask editedTask) {
        if ( repeatableTaskRepository.findById(editedTask.getId()).isPresent())
            return repeatableTaskRepository.save(editedTask).getId();

        if ( tasksRepository.findById(editedTask.getId()).isPresent() ) {
            tasksRepository.deleteById(editedTask.getId());
            return repeatableTaskRepository.save(editedTask).getId();
        }

        return -1L;
    }
//
    public Long removeTask(Long id, Long userId) {
        if (tasksRepository.existsById(id)) {
            if(!tasksRepository.findById(id).get().getOwnerId().equals(userId))
                return -2L;
            tasksRepository.deleteById(id);
            return 0L;
        }
        if (repeatableTaskRepository.existsById(id)) {
            if(!repeatableTaskRepository.findById(id).get().getOwnerId().equals(userId))
                return -2L;
            repeatableTaskRepository.deleteById(id);
            return 0L;
        }
        return -1L;
    }

    public Long taskDone(Long id, Long userId) {
        if (tasksRepository.existsById(id)) {
            if(!tasksRepository.findById(id).get().getOwnerId().equals(userId))
                return -2L;
            tasksRepository.deleteById(id);
            return 0L;
        }
        if (repeatableTaskRepository.findById(id).isPresent()) {
            if(!repeatableTaskRepository.findById(id).get().getOwnerId().equals(userId))
                return -2L;
            RepeatableTask task = repeatableTaskRepository.findById(id).get();
            task.setLastDone(new Date(System.currentTimeMillis()));
            repeatableTaskRepository.save(task);
            return 0L;
        }
        return -1L;
    }

    public Long taskUndone(Long id, Long userId) {
        if (repeatableTaskRepository.findById(id).isPresent()) {
            if(!repeatableTaskRepository.findById(id).get().getOwnerId().equals(userId))
                return -2L;
            RepeatableTask task = repeatableTaskRepository.findById(id).get();
            task.setLastDone(new Date(System.currentTimeMillis()- 24*60*60*1000));
            repeatableTaskRepository.save(task);
            return 0L;
        }
        return -1L;
    }
}

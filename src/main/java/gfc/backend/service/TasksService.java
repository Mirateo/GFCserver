package gfc.backend.service;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.repository.RepeatableTaskRepository;
import gfc.backend.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
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

        return (long) -1;
    }

    public Long editReTask(RepeatableTask editedTask) {
        if ( repeatableTaskRepository.findById(editedTask.getId()).isPresent())
            return repeatableTaskRepository.save(editedTask).getId();

        if ( tasksRepository.findById(editedTask.getId()).isPresent() ) {
            tasksRepository.deleteById(editedTask.getId());
            return repeatableTaskRepository.save(editedTask).getId();
        }

        return (long) -1;
    }

    public Long removeTask(Long id) {
        if (tasksRepository.existsById(id)) {
            tasksRepository.deleteById(id);
            return 0L;
        }

        if (repeatableTaskRepository.existsById(id)) {
            repeatableTaskRepository.deleteById(id);
            return 0L;
        }

        return (long) -1;
    }

    public Long taskDone(Long id) {
        if (tasksRepository.existsById(id)) {
            tasksRepository.deleteById(id);
            return 0L;
        }

        if (repeatableTaskRepository.findById(id).isPresent()) {
            RepeatableTask task = repeatableTaskRepository.findById(id).get();
            task.setDoneToday(true);
            task.setLastDone(new Date(System.currentTimeMillis()));
            repeatableTaskRepository.save(task);
            return 0L;
        }

        return (long) -1;
    }
}

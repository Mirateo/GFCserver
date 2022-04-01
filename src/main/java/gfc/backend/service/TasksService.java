package gfc.backend.service;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.DoneTask;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.repository.DoneTasksRepository;
import gfc.backend.repository.RepeatableTaskRepository;
import gfc.backend.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map.Entry;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksService {
    TasksRepository tasksRepository;
    DoneTasksRepository doneTasksRepository;
    RepeatableTaskRepository repeatableTaskRepository;

    public List<Task> getAllUserTasks(Long ownerId) {
        return tasksRepository.findAllByOwnerId(ownerId);
    }

    public List<RepeatableTask> getAllUserRepeatableTasks(Long ownerId) {
        return repeatableTaskRepository.findAllByOwnerId(ownerId);
    }

    public Long addTask(TaskDTO newTask) {
        System.out.println(newTask.toString());
        if (!newTask.getRepeatable())
            return tasksRepository.save(new Task(newTask)).getId();
        else
            return repeatableTaskRepository.save(new RepeatableTask(newTask)).getId();
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

    public Long removeTask(Long id) {
        if (tasksRepository.existsById(id)) {
            tasksRepository.deleteById(id);
            return 0L;
        }
        if (repeatableTaskRepository.existsById(id)) {
            repeatableTaskRepository.deleteById(id);
            return 0L;
        }
        return -1L;
    }

    public Entry<RepeatableTask, Task> taskDone(Long id) {
        if (tasksRepository.findById(id).isPresent()) {
            Task task = tasksRepository.findById(id).get();
            DoneTask done = new DoneTask(task);
            tasksRepository.deleteById(id);
            doneTasksRepository.save(done);
            return new AbstractMap.SimpleEntry<RepeatableTask, Task>(null, task);
        }
        if (repeatableTaskRepository.findById(id).isPresent()) {
            RepeatableTask task = repeatableTaskRepository.findById(id).get();
            task.setLastDone(new Date(System.currentTimeMillis()));
            repeatableTaskRepository.save(task);

            return new AbstractMap.SimpleEntry<RepeatableTask, Task>(task, null);
        }
        return null;
    }

    public List<DoneTask> getAllUserDoneTasks(Long id) {
        return doneTasksRepository.findAllByOwnerId(id);
    }

    public RepeatableTask taskUndone(Long id) {
        if (repeatableTaskRepository.findById(id).isPresent()) {
            RepeatableTask task = repeatableTaskRepository.findById(id).get();
            task.setLastDone(new Date(System.currentTimeMillis()- 24*60*60*1000));
            repeatableTaskRepository.save(task);

            return task;
        }
        return null;
    }
}

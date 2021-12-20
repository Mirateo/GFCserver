package gfc.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/all/{ownerId}")
    List<Task> getAllUserTasks(@PathVariable Long ownerId) {
        return tasksService.getAllUserTasks(ownerId);
    }

    @GetMapping("/allre/{ownerId}")
    List<RepeatableTask> getAllUserRepeatableTasks(@PathVariable Long ownerId) {
        System.out.println("Sent:\n" + tasksService.getAllUserRepeatableTasks(ownerId));
        return tasksService.getAllUserRepeatableTasks(ownerId);
    }

    @PostMapping("/add")
    public Long addTask(@RequestBody TaskDTO newTask) throws JsonProcessingException {
        System.out.println("Your new task: " + newTask.toString());
        return tasksService.addTask(newTask);
    }

    @PostMapping("/edit")
    public Long editTask(@RequestBody Task editedTask) {
        return tasksService.editTask(editedTask);
    }

    @PostMapping("/editRe")
    public Long editTask(@RequestBody RepeatableTask editedTask) {
        return tasksService.editReTask(editedTask);
    }

    @GetMapping("/remove/{id}")
    public Long removeTask(@PathVariable Long id) {
        return tasksService.removeTask(id);
    }

    @GetMapping("/done/{id}")
    public Long taskDone(@PathVariable Long id) {
        return tasksService.taskDone(id);
    }

    @GetMapping("/undone/{id}")
    public Long taskUndone(@PathVariable Long id) {
        return tasksService.taskUndone(id);
    }
}

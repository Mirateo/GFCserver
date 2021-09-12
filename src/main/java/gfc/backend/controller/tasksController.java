package gfc.backend.controller;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.Task;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class tasksController {
    private final TasksService tasksService;

    @GetMapping("/{ownerId}")
    List<Task> getAllUserTasks(@PathVariable long ownerId) {
        return tasksService.getAllUserTasks(ownerId);
    }

    @PostMapping("/add")
    public Long addTask(@RequestBody TaskDTO newTask) {
        return tasksService.addTask(newTask);
    }

    @PostMapping("/edit")
    public Long editTask(@RequestBody Task editedTask) {
        return tasksService.editTask(editedTask);
    }

}

package gfc.backend.controller;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import gfc.backend.service.FamilyService;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.AbstractMap;
import java.util.Map.Entry;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksController {
    UserRepository userRepository;
    private final FamilyService familyService;
    private final TasksService tasksService;

    private ResponseEntity<?> generateResponse(Long response) {
        if (response == -2) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        else if (response == -1) {
            return new ResponseEntity<>("Task doesn't exist", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllUserTasks() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            return ResponseEntity.badRequest().body("User doesn't exist!");

        return new ResponseEntity<>(tasksService.getAllUserTasks(user.get().getId()), HttpStatus.OK);
    }

    @GetMapping("/allre")
    ResponseEntity<?> getAllUserRepeatableTasks() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            return ResponseEntity.badRequest().body("User doesn't exist!");

        return new ResponseEntity<>(tasksService.getAllUserRepeatableTasks(user.get().getId()), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskDTO newTask)  {
        return new ResponseEntity<>(tasksService.addTask(newTask), HttpStatus.OK);
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
    public ResponseEntity<?> removeTask(@PathVariable Long id) {
        return generateResponse(tasksService.removeTask(id));
    }

    @GetMapping("/done/{id}")
    public ResponseEntity<?> taskDone(@PathVariable Long id) {
        Entry<RepeatableTask, Task> response = tasksService.taskDone(id);
        if (response == null) {
            return ResponseEntity.badRequest().body("Task doesn't exist");
        }

        return familyService.addPoints(response);
    }

    @GetMapping("/undone/{id}")
    public ResponseEntity<?> taskUndone(@PathVariable Long id) {
        RepeatableTask task = tasksService.taskUndone(id);
        if (task == null) {
            return ResponseEntity.badRequest().body("Task doesn't exist");
        }

        task.setPoints(-task.getPoints());

        return familyService.addPoints(new AbstractMap.SimpleEntry<>(task, null));
    }


    @GetMapping("/full")
    ResponseEntity<?> getFamilyTasks() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            return ResponseEntity.badRequest().body("User doesn't exist!");

        Iterable<User> family = userRepository.findByEmail(user.get().getEmail());

        List<Task> tasksList = new ArrayList<>();

        family.forEach(member -> tasksList.addAll(tasksService.getAllUserTasks(member.getId())));

        return new ResponseEntity<>(tasksList, HttpStatus.OK);
    }


    @GetMapping("/fullRe")
    ResponseEntity<?> getFamilyReTasks() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            return ResponseEntity.badRequest().body("User doesn't exist!");

        Iterable<User> family = userRepository.findByEmail(user.get().getEmail());

        List<RepeatableTask> tasksList = new ArrayList<>();

        family.forEach(member -> tasksList.addAll(tasksService.getAllUserRepeatableTasks(member.getId())));

        return new ResponseEntity<>(tasksList, HttpStatus.OK);
    }



}

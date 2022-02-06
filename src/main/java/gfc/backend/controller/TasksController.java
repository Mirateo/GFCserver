package gfc.backend.controller;

import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksController {
    private final TasksService tasksService;
    UserRepository userRepository;


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

    @GetMapping("/remove/{id}")
    public ResponseEntity<?> removeTask(@PathVariable Long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.removeTask(id, userRepository.findByUsername(username).get().getId()));
    }

    @GetMapping("/done/{id}")
    public ResponseEntity<?> taskDone(@PathVariable Long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.taskDone(id, userRepository.findByUsername(username).get().getId()));
    }

    @GetMapping("/undone/{id}")
    public ResponseEntity<?> taskUndone(@PathVariable Long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.taskUndone(id, userRepository.findByUsername(username).get().getId()));

    }
}

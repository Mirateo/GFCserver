package gfc.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfc.backend.dto.MessageResponse;
import gfc.backend.dto.TaskDTO;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Task;
import gfc.backend.repository.UserRepository;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TasksController {
    private final TasksService tasksService;
    UserRepository users;


    @GetMapping("/all/{ownerId}")
    ResponseEntity<?> getAllUserTasks(@PathVariable Long ownerId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (users.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        if (!ownerId.equals(users.findByUsername(username).get().getId()))
           return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(tasksService.getAllUserTasks(ownerId), HttpStatus.OK);
    }

    @GetMapping("/allre/{ownerId}")
    ResponseEntity<?> getAllUserRepeatableTasks(@PathVariable Long ownerId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (users.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        if (!ownerId.equals(users.findByUsername(username).get().getId()))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(tasksService.getAllUserRepeatableTasks(ownerId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskDTO newTask,
                        @AuthenticationPrincipal UsernamePasswordAuthenticationToken user)  {
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (users.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.removeTask(id, users.findByUsername(username).get().getId()));
    }

    @GetMapping("/done/{id}")
    public ResponseEntity<?> taskDone(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (users.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.taskDone(id, users.findByUsername(username).get().getId()));
    }

    @GetMapping("/undone/{id}")
    public ResponseEntity<?> taskUndone(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (users.findByUsername(username).isEmpty())
            return new ResponseEntity<>("User doesn't exist", HttpStatus.UNAUTHORIZED);
        return generateResponse(tasksService.taskUndone(id, users.findByUsername(username).get().getId()));

    }
}

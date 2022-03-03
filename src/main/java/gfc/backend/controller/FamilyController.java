package gfc.backend.controller;

import gfc.backend.dto.SignupChildRequest;
import gfc.backend.dto.SignupRequest;
import gfc.backend.model.Relationship;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import gfc.backend.service.FamilyService;
import gfc.backend.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyController {
    private final FamilyService familyService;

    @GetMapping("/")
    public ResponseEntity<?> getFamily() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return familyService.getFamily(username);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addChild(@Valid @RequestBody SignupChildRequest request) {
        return familyService.addChild(request);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeChild(@PathVariable(value = "id")  String id) {
        return familyService.removeChild(Long.parseLong(id));
    }
}


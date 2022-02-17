package gfc.backend.controller;

import gfc.backend.dto.SignupChildRequest;
import gfc.backend.dto.SignupRequest;
import gfc.backend.model.Relationship;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
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
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public ResponseEntity<?> getFamily() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user =  userRepository.findByUsername(username);
        if (user.isPresent()){
            Iterable<User> family = userRepository.findByEmail(user.get().getEmail());
            List<User> familyList = new ArrayList<>();
            family.forEach(familyList::add);
            return ResponseEntity.ok(familyList);
        }
        else {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addChild(@Valid @RequestBody SignupChildRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Błąd: Nazwa użytkownika zajęta!");
        }

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), "CHILD", request.getFriendlyName());
        userRepository.save(user);
        return ResponseEntity.ok("Dziecko zarejestrowane pomyślnie!");
    }


    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeChild(@PathVariable(value = "id")  String id) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + id);

        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("Błąd: Członek rodziny nie istnieje!");
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok("Członek rodziny usunięty pomyślnie!");
    }
}


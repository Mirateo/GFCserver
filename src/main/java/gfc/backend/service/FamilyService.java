package gfc.backend.service;

import gfc.backend.dto.SignupChildRequest;
import gfc.backend.model.RepeatableTask;
import gfc.backend.model.Reward;
import gfc.backend.model.Task;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getFamily(String username) {
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


    public ResponseEntity<?> addChild(SignupChildRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Błąd: Nazwa użytkownika zajęta!");
        }

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), "CHILD", request.getFriendlyName());
        userRepository.save(user);
        return ResponseEntity.ok("Dziecko zarejestrowane pomyślnie!");
    }


    public ResponseEntity<?> removeChild(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("Błąd: Członek rodziny nie istnieje!");
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok("Członek rodziny usunięty pomyślnie!");
    }

    public ResponseEntity<?> addPoints(Map.Entry<RepeatableTask, Task> tasks, Integer multiplier) {
        Long ownerId;
        Long points;
        Boolean ifOwn;

        if (tasks.getKey() == null) {
            ownerId = tasks.getValue().getOwnerId();
            points = tasks.getValue().getPoints();
            ifOwn = tasks.getValue().getOwn();

        }
        else {
            ownerId = tasks.getKey().getOwnerId();
            points = tasks.getKey().getPoints();
            ifOwn = tasks.getKey().getOwn();
        }

        Optional<User> user = userRepository.findById(ownerId);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("Błąd: Członek rodziny nie istnieje!");
        }
        User justUser = user.get();
        if (ifOwn && justUser.getRole().equals("CHILD")){
            return ResponseEntity.ok().body(justUser.getPoints());
        }

        justUser.setPoints(justUser.getPoints() + multiplier * points);
        userRepository.save(justUser);

        return ResponseEntity.ok().body(justUser.getPoints());
    }

    public Reward payPoints(Reward reward, Long multiplier) {
        User owner = reward.getOwner();

        if (owner == reward.getReporter() && owner.getRole().equals("CHILD")){
            return reward;
        }
        owner.setPoints(owner.getPoints() - (reward.getPoints() * multiplier));
        userRepository.save(owner);
        reward.setOwner(owner);
        return reward;
    }
}

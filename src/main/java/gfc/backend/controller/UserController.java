package gfc.backend.controller;

import gfc.backend.dto.MessageResponse;
import gfc.backend.dto.SignupRequest;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(signupRequest.getEmail().isEmpty() || signupRequest.getPassword().isEmpty() || signupRequest.getUsername().isEmpty()){
            return ResponseEntity.badRequest().body("Błąd: Zapytanie niekompletne!");
        }

        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body("Błąd: Nazwa użytkownika zajęta!");
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body("Błąd: Adres email jest przypisany do innego konta!");
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("Użytkownik zarejestrowany pomyślnie!");
    }
}

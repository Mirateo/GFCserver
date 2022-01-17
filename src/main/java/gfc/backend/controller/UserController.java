package gfc.backend.controller;

import gfc.backend.dto.MessageResponse;
import gfc.backend.dto.SignupRequest;
import gfc.backend.dto.UserInfo;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import java.util.Optional;

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
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

    @GetMapping("/user_info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UsernamePasswordAuthenticationToken creds){
        System.out.println(creds.getPrincipal().toString());
        System.out.println(creds.getPrincipal());
        Optional<User> user = userRepository.findByUsername(creds.getPrincipal().toString());
        System.out.println(user.get().toString());
        if (user.isPresent()){
            return new ResponseEntity<>(new UserInfo(user.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Invalid token.", HttpStatus.NOT_FOUND);
        }
    }
}

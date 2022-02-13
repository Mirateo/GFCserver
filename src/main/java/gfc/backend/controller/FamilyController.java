package gfc.backend.controller;

import gfc.backend.model.Relationship;
import gfc.backend.model.User;
import gfc.backend.repository.FamilyRepository;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final FamilyRepository familyRepository;

    @GetMapping("/")
    public ResponseEntity<?> getFamily(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> maybeUser = userRepository.findByUsername(username);
        if (maybeUser.isPresent()){
            User user = maybeUser.get();
            Optional<Relationship> family;
            if(user.getRole().equals("PARENT")) {
                if((family = familyRepository.findByParent(user)).isPresent()){
                    List<User> memberList = family.get().getChildren();
                    memberList.add(user);
                }
            }
            else {

                if((family = familyRepository.findByParent(user)).isPresent()){
                    List<User> memberList = family.get().getChildren();
                    memberList.add(user);
                }
            }

            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
    }

}


// JEBAĆ TO WSZYSTKO - CZŁONKOWIE TA TABELA JEST NIEPOTRZEBNA. CAŁA RODZINA MA TAKI SAM E-MAIL, ALE INNĄ ROLĘ.

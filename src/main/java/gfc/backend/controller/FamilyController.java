package gfc.backend.controller;

import gfc.backend.model.Relationship;
import gfc.backend.model.User;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyController {
    @Autowired
    private final UserRepository userRepository;


    @GetMapping("/")
    public ResponseEntity<?> getFamily() {
        return null;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addChild() {
        return null;
    }

}


// JEBAĆ TO WSZYSTKO - CZŁONKOWIE TA TABELA JEST NIEPOTRZEBNA. CAŁA RODZINA MA TAKI SAM E-MAIL, ALE INNĄ ROLĘ.

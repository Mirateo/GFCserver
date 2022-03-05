package gfc.backend.controller;

import gfc.backend.dto.SignupChildRequest;
import gfc.backend.service.FamilyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


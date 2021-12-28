package gfc.backend.controller;

import gfc.backend.config.LoginCredentials;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/tasks")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials){

    }


}

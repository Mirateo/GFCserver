package gfc.backend.controller;


import gfc.backend.dto.RewardDTO;
import gfc.backend.model.Reward;
import gfc.backend.model.User;
import gfc.backend.service.FamilyService;
import gfc.backend.service.RewardsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rewards")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RewardsController {
    private final RewardsService rewardsService;
    private final FamilyService familyService;

    @GetMapping("/")
    ResponseEntity<?> getRewards() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Reward> rewards = rewardsService.getRewards(username);

        if(rewards == null) {
            return new ResponseEntity<>("Użytkownik nie został znaleziony.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(rewards);
    }


    @PostMapping("/add")
    ResponseEntity<?> addReward(@RequestBody RewardDTO reward) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long resp = rewardsService.add(reward, username);

        if(resp != null) {
            return ResponseEntity.ok(resp);
        } else {
            return  ResponseEntity.badRequest().body("Przesłane dane niepoprawne.");
        }
    }

    @PostMapping("/edit")
    ResponseEntity<?> editReward(@RequestBody Reward reward) {
        Long resp = rewardsService.edit(reward);

        if (resp != null) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body("Przesłane dane niepoprawne.");
        }
    }

    @PostMapping("/select/{id}")
    ResponseEntity<?> selectReward(@PathVariable Long id) {
        Reward resp = rewardsService.select(id);

        if(resp == null) {
            return ResponseEntity.ok().body(-1L);
        } else {
            return familyService.payPoints(resp, 1);
        }
    }

    @PostMapping("/accept/{id}")
    ResponseEntity<?> acceptReward(@PathVariable Long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long resp = rewardsService.accept(id, username);

        if(resp != null) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body("Przesłane dane niepoprawne.");
        }

    }

    @PostMapping("/unselect/{id}")
    ResponseEntity<?> unSelectReward(@PathVariable Long id) {
        Reward resp = rewardsService.unselect(id);

        if(resp == null) {
            return ResponseEntity.badRequest().body("Przesłane dane niepoprawne.");
        } else {
            return familyService.payPoints(resp, -1);
        }
    }

    @PostMapping("/delete/{id}")
    ResponseEntity<?> deleteReward(@PathVariable Long id) {
        Long resp = rewardsService.delete(id);

        if(resp != null) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body("Przesłane dane niepoprawne.");
        }
    }


}

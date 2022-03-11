package gfc.backend.service;

import gfc.backend.dto.RewardDTO;
import gfc.backend.model.Reward;
import gfc.backend.model.User;
import gfc.backend.repository.RewardRepository;
import gfc.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RewardsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RewardRepository rewardsRepository;


    public List<Reward> getRewards(String username) {
        List<Reward> rewards = new ArrayList<>();
        if (! userRepository.existsByUsername(username)){
            return null;
        }
        User requester = userRepository.findByUsername(username).get();

        if(requester.getRole().equals("PARENT")) {
            rewardsRepository.findByReporter(requester).forEach(rewards::add);
        }
        else {
            rewardsRepository.findByOwner(requester).forEach(rewards::add);
        }

        return rewards;
    }

    public Long add(RewardDTO rewardDto, String username) {
        Optional<User> requesterOpt = userRepository.findByUsername(username);
        if (requesterOpt.isEmpty()){
            return null;
        }
        User requester =  requesterOpt.get();
        User reporter;

        if (rewardDto.getReporter() == null) {
            User parent = new User();
            for (User user : userRepository.findByEmail(requester.getEmail())) {
                parent = user;
                if (parent.getRole().equals("PARENT")) {
                    break;
                }
            }
            reporter = parent;

        } else {
            reporter = userRepository.findById(rewardDto.getReporter()).get();
        }

        User owner = userRepository.findById(rewardDto.getOwner()).get();

        Reward reward = new Reward(rewardDto.getTitle(), rewardDto.getDescription(), reporter, owner ,rewardDto.getChosen(), rewardDto.getPoints());
        rewardsRepository.save(reward);
        return reward.getRewardId();
    }

    public Long edit(Reward reward) {
        return rewardsRepository.save(reward).getRewardId();
    }

    public Long select(Long id) {
        Optional<Reward> optRew = rewardsRepository.findByRewardId(id);
        if (optRew.isEmpty()) {
            return null;
        }
        Reward reward = optRew.get();
        if(reward.getPoints() < reward.getOwner().getPoints()) {
            reward.setChosen(true);
            return rewardsRepository.save(reward).getRewardId();
        }
        return 0L;
    }

    public Long accept(Long id) {
        Optional<Reward> optRew = rewardsRepository.findByRewardId(id);
        if (optRew.isEmpty()) {
            return null;
        }
        Reward reward = optRew.get();
        rewardsRepository.delete(reward);
        return 0L;
    }

    public Long unselect(Long id) {
        Optional<Reward> optRew = rewardsRepository.findByRewardId(id);
        if (optRew.isEmpty()) {
            return null;
        }
        Reward reward = optRew.get();
        reward.getOwner().setPoints(reward.getOwner().getPoints() + reward.getPoints());
        reward.setChosen(false);
        return rewardsRepository.save(reward).getRewardId();
    }

    public Long delete(Long id) {
        if (rewardsRepository.existsById(id)) {
            rewardsRepository.deleteById(id);
            return 0L;
        }
        return -1L;
    }
}

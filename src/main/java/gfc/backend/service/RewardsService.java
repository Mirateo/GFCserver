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
        if (! userRepository.existsByUsername(username)){
            return null;
        }
        User requester = userRepository.findByUsername(username).get();

        if(!rewardDto.getOwner().equals(requester.getId()) && !rewardDto.getOwner().equals(requester.getId())) {
            return null;
        }

        Reward reward = new Reward(rewardDto.getTitle(), rewardDto.getDescription(), rewardDto.getChosen(), rewardDto.getPoints());

        reward.setOwner(userRepository.findById(rewardDto.getOwner()).get());
        reward.setReporter(userRepository.findById(rewardDto.getReporter()).get());

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
}

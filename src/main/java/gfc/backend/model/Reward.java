package gfc.backend.model;

import gfc.backend.dto.RewardDTO;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    Long rewardId;

    @Getter
    @Setter
    @NotNull
    String title;

    @Getter
    @Setter
    @Nullable
    String description;

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    User reporter;

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    User owner;

    @Getter
    @Setter
    Boolean chosen = false;

    @Getter
    @Setter
    @NotNull
    Long points = 0L;

    public Reward(@NotNull String title, @Nullable String description, Boolean chosen, Long points) {
        this.title = title;
        this.description = description;
        this.chosen = chosen;
        this.points = points;
    }

    public Reward(String title, String description, User reporter, User owner, Boolean chosen, Long points) {
        super();
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.owner = owner;
        this.chosen = chosen;
        this.points = points;
    }
}

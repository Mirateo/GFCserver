package gfc.backend.model;

import lombok.*;

import javax.persistence.*;

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
    String title;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    @ManyToOne
    User reporter;

    @Getter
    @Setter
    @ManyToOne
    User owner;

    @Getter
    @Setter
    Boolean chosen;
}

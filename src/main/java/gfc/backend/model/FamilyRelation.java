package gfc.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FamilyRelations")
public class FamilyRelation {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationID;

    @Getter
    @Setter
    @NotNull
    @OneToOne
    @JoinColumn(name = "id")
    private User userId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id")
    private User childrenId;

}

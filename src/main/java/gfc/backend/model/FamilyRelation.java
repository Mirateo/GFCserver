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
    @Getter
    @Setter
    @OneToOne
    @Id
    private User userId;

    @Getter
    @Setter
    @ManyToOne
    private User childrenId;

}

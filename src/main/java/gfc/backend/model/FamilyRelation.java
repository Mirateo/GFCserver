package gfc.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FamilyRelations")
public class FamilyRelation {
    @Getter
    @Setter
    @Id
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private User parentId;

    @Getter
    @Setter
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private List<User> childrenId;

}

package gfc.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Relationship")
public class FamilyRelation {
    @Id
    @Getter
    @Setter
    private Long relationId;

    @Getter
    @Setter
    @OneToOne(fetch=FetchType.LAZY)
    private User parentId;

    @Getter
    @Setter
    @OneToMany(fetch=FetchType.LAZY)
    private List<User> childrenId;

}

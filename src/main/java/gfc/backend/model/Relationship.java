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
public class Relationship {
    @Id
    @Getter
    @Setter
    private Long relationId;

    @Getter
    @Setter
    @OneToOne(fetch=FetchType.LAZY)
    private User parent;

    @Getter
    @Setter
    @OneToMany(fetch=FetchType.LAZY)
    private List<User> children;

}

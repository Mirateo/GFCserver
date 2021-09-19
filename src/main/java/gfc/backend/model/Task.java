package gfc.backend.model;

import com.sun.istack.NotNull;
import gfc.backend.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long ownerId;

    @NotNull
    private String name;

    private String description;

    private Long points;

    public Task(TaskDTO newTask) {
        this.ownerId = newTask.getOwnerId();
        this.name = newTask.getName();
        this.description = newTask.getDescription();
        this.points = newTask.getPoints();
    }

    public Task(RepeatableTask editedTask) {
        this.ownerId = editedTask.getOwnerId();
        this.name = editedTask.getName();
        this.description = editedTask.getDescription();
        this.points = editedTask.getPoints();
    }
}

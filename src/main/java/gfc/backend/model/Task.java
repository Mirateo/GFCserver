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

    private Boolean own = false;

    public Task(TaskDTO newTask) {
        this.ownerId = newTask.getOwnerId();
        this.name = newTask.getName();
        this.description = newTask.getDescription();
        this.points = newTask.getPoints();
        this.own = newTask.getOwn();
    }

    public Task(RepeatableTask editedTask) {
        this.ownerId = editedTask.getOwnerId();
        this.name = editedTask.getName();
        this.description = editedTask.getDescription();
        this.points = editedTask.getPoints();
    }
}


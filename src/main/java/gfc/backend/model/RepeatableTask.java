package gfc.backend.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import gfc.backend.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RepeatableTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long ownerId;

    @NotNull
    private String name;

    private String description;

    private Long points;

    @Nullable
    private Date lastDone;


    public RepeatableTask(TaskDTO newTask) {
        this.ownerId = newTask.getOwnerId();
        this.name = newTask.getName();
        this.description = newTask.getDescription();
        this.points = newTask.getPoints();
        this.lastDone = new Date(0);
    }

    public RepeatableTask(RepeatableTask editedTask) {
        this.id = editedTask.getId();
        this.ownerId = editedTask.getOwnerId();
        this.name = editedTask.getName();
        this.description = editedTask.getDescription();
        this.points = editedTask.getPoints();
        this.lastDone = editedTask.getLastDone();
    }
}

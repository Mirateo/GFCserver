package gfc.backend.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import gfc.backend.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class DoneTask {
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

    @NotNull
    private Date timestamp;

    public DoneTask(Task newTask) {
        this.ownerId = newTask.getOwnerId();
        this.name = newTask.getName();
        this.description = newTask.getDescription();
        this.points = newTask.getPoints();
        this.own = newTask.getOwn();
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public DoneTask(RepeatableTask editedTask) {
        this.ownerId = editedTask.getOwnerId();
        this.name = editedTask.getName();
        this.description = editedTask.getDescription();
        this.points = editedTask.getPoints();
    }
}

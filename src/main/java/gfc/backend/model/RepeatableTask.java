package gfc.backend.model;

import gfc.backend.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RepeatableTask extends Task{
    private Boolean doneToday;
    private Date lastDone;

    public RepeatableTask(TaskDTO newTask) {
        super(newTask);
        doneToday = false;
        lastDone = new Date(System.currentTimeMillis());
    }
}

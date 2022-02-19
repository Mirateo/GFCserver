package gfc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long ownerId;
    private String name;
    private String description;
    private Long points;
    private Boolean repeatable;
    private Boolean own = false;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Boolean getOwn() {
        return own;
    }

    public void setOwn(Boolean own) {
        this.own = own;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", repeatable=" + repeatable +
                '}';
    }
}

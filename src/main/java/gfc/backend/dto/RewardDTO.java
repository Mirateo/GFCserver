package gfc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO {
    @Getter
    @Setter
    @NotNull
    String title;

    @Getter
    @Setter
    @Nullable
    String description;

    @Getter
    @Setter
    @Nullable
    Long reporter;

    @Getter
    @Setter
    @NotNull
    Long owner;

    @Getter
    @Setter
    @Nullable
    Boolean chosen = false;

    @Getter
    @Setter
    @NotNull
    Long points = 0L;

    @Override
    public String toString() {
        return "RewardDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reporter=" + reporter +
                ", owner=" + owner +
                ", chosen=" + chosen +
                ", points=" + points +
                '}';
    }
}

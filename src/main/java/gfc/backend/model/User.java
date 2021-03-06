package gfc.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String role = "PARENT";

    @Getter
    @Setter
    private String friendlyName;

    @Getter
    @Setter
    private Long points = 0L;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.friendlyName = "Rodzic";
    }

    public User(String username, String email, String password, String role, String friendlyName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.friendlyName = friendlyName;
    }

    public void edit(User newData) {
        this.username = newData.username;
        this.email = newData.email;
        if (newData.password != null) {
            this.password = newData.password;
        }
        this.role = newData.role;
        this.friendlyName = newData.friendlyName;
    }
}

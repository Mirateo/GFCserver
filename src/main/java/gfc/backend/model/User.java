package gfc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String role = "PARENT";

    private String friendlyName= "Rodzic";

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
        this.password = newData.password;
        this.role = newData.role;
        this.friendlyName = newData.friendlyName;
    }
}

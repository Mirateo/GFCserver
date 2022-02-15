package gfc.backend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupChildRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 1, max = 20)
    private String friendlyName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignupChildRequest(@NotBlank @Size(min = 3, max = 20) String username, @Size(min = 1, max = 20) String friendlyName, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 40) String password) {
        this.username = username;
        this.friendlyName = friendlyName;
        this.email = email;
        this.password = password;
    }
}

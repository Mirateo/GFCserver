package gfc.backend.dto;

import gfc.backend.model.User;

public class UserInfo {
    private final Long id;
    private final String username;
    private final String email;
    private final String role = "PARENT";
    private final String friendlyName= "Rodzic";

    public UserInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}

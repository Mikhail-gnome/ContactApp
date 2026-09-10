package Mikhail_gnome.com.github.contactApp.model.entity;

import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ContactOwner {
    private String id;
    private String username;
    private String description;
    private String email;
    @JsonIgnore
    private String password;
    private AppRole role;

    private List<Contact> contacts = new ArrayList<>();

    public ContactOwner() {
        this.id = UUID.randomUUID().toString();
    }

    public ContactOwner(String username, String description) {
        this();
        this.username = username;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactOwner that = (ContactOwner) o;
        return Objects.equals(id, that.id)
                && Objects.equals(username, that.username)
                && Objects.equals(description, that.description)
                && Objects.equals(email, that.email)
                && Objects.equals(password, that.password)
                && role == that.role
                && Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, description, email, password, role, contacts);
    }
}

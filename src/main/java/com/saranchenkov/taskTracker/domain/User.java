package com.saranchenkov.taskTracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Ivan on 13.10.2017.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonView(TaskViews.ExtendedTask.class)
    private int id;

    @JsonView(TaskViews.ExtendedTask.class)
    private String email;

    private String password;

    @Column(name = "first_name")
    @JsonView(TaskViews.ExtendedTask.class)
    private String firstName;

    @Column(name = "last_name")
    @JsonView(TaskViews.ExtendedTask.class)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active")
    private boolean enabled = false;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    Set<Project> projects = new HashSet<>();

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.enabled = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", enabled=" + enabled +
                '}';
    }
}

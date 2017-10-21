package com.saranchenkov.taskTracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivan on 13.10.2017.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonView(TaskViews.ExtendedTask.class)
    private int id;

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
    private boolean isActive = false;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    Set<Project> projects = new HashSet<>();

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = false;
    }
}

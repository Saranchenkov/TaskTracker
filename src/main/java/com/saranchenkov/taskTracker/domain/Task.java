package com.saranchenkov.taskTracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Ivan on 13.10.2017.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {

    public static final String COMMENTS = "comments";
    public static final String DEVELOPER = "developer";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    @JsonView(TaskViews.CommonTask.class)
    private int id;

    @JsonView(TaskViews.CommonTask.class)
    private String name;

    @JsonView(TaskViews.CommonTask.class)
    private String description;

    @JsonView(TaskViews.CommonTask.class)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(mappedBy = "task")
    @JsonView(TaskViews.ExtendedTask.class)
    @OrderBy("desc")
    SortedSet<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    @JsonView(TaskViews.ExtendedTask.class)
    private User developer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    public Task(String description, TaskStatus status) {
        this.description = description;
        this.status = status;
    }

    public Task(String description, TaskStatus status, User developer) {
        this.description = description;
        this.status = status;
        this.developer = developer;
    }
}

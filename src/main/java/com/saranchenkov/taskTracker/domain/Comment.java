package com.saranchenkov.taskTracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Ivan on 13.10.2017.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment implements Comparable<Comment>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    @JsonView(TaskViews.ExtendedTask.class)
    private int id;
    @JsonView(TaskViews.ExtendedTask.class)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    public Comment(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int compareTo(Comment o) {
        return o.getId() - this.id;
    }
}

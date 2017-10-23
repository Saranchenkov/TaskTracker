package com.saranchenkov.taskTracker.repository;

import com.saranchenkov.taskTracker.domain.Task;
import com.saranchenkov.taskTracker.domain.TaskStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Ivan on 16.10.2017.
 */
public interface TaskRepository extends JpaRepository<Task, Integer>{

    @Query("select t from Task t where t.project.id = :id order by t.id desc ")
    List<Task> findTasksByProjectId(@Param("id") int id);

    @Query("select t from Task t where t.project.id = :projectId and t.developer.id = :developerId order by t.id desc ")
    List<Task> findFilteredTasks(@Param("projectId") int projectId, @Param("developerId") int developerId);

    @Query("select t from Task t where t.name = :name")
    Task findTaskByName(@Param("name") String name);

    @EntityGraph(attributePaths = {Task.COMMENTS, Task.DEVELOPER})
    @Query("select distinct t from Task t where t.id = :id")
    Task findTaskWithCommentsAndDeveloper(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("update Task t set t.status = :status where t.id = :id")
    int updateTaskStatus(@Param("status") TaskStatus status, @Param("id") int id);
}

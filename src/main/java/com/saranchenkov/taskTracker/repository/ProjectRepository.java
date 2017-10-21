package com.saranchenkov.taskTracker.repository;

import com.saranchenkov.taskTracker.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Ivan on 15.10.2017.
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("select p from Project p where p.name = :name")
    Project findByName(@Param("name") String name);

//    @Query("select p.tasks from Project p where p.id = :id ")
//    List<Task> findTasks(@Param("id") int id);

}

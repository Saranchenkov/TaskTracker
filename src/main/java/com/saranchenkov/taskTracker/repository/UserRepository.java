package com.saranchenkov.taskTracker.repository;

import com.saranchenkov.taskTracker.domain.Project;
import com.saranchenkov.taskTracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Ivan on 14.10.2017.
 */

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u.projects from User u where u.id = :id ")
    List<Project> getProjects(@Param("id") int id);

    @Query("select u from User u where u.firstName like ?1% and u.lastName like ?2% and u.role = 'DEVELOPER'")
    List<User> findDevelopersByName(String firstName, String lastName);

    @Query("select u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("update User u set u.enabled=true where u.id = :id")
    int enableUser(@Param("id") int id);
}

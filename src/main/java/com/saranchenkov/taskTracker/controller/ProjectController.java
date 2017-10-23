package com.saranchenkov.taskTracker.controller;

import com.saranchenkov.taskTracker.domain.Project;
import com.saranchenkov.taskTracker.repository.ProjectRepository;
import com.saranchenkov.taskTracker.repository.UserRepository;
import com.saranchenkov.taskTracker.service.AppService;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 14.10.2017.
 */

@RestController
@RequestMapping("/projects")
@Slf4j
public class ProjectController {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AppService service;

    @Autowired
    public ProjectController(UserRepository userRepository, ProjectRepository projectRepository, AppService service) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.service = service;
    }

    @GetMapping
    public List<Project> getProjects(HttpServletRequest request){
        Util.printLog(request);
        int managerId = Util.getCustomPrincipal(request).getId();
        return userRepository.getProjects(managerId)
                                            .stream()
                                            .sorted(Comparator.comparing(Project::getId).reversed())
                                            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteProject(@PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        projectRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> saveProject(@RequestBody String projectName, HttpServletRequest request){
        Util.printLog(request);
        int managerId = Util.getCustomPrincipal(request).getId();
        return Objects.nonNull(projectRepository.findByName(projectName)) ?
                new ResponseEntity(HttpStatus.CONFLICT) :
                new ResponseEntity<>(service.saveProject(managerId, projectName), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/developer")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> addDeveloper(@RequestBody int developerId, @PathVariable("id") int projectId, HttpServletRequest request){
        Util.printLog(request);
        return service.addDeveloperToProject(projectId, developerId) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}

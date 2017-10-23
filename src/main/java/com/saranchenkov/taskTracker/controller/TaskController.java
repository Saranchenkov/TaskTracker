package com.saranchenkov.taskTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.domain.Task;
import com.saranchenkov.taskTracker.domain.TaskStatus;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import com.saranchenkov.taskTracker.repository.TaskRepository;
import com.saranchenkov.taskTracker.service.AppService;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ivan on 16.10.2017.
 */
@RestController
@RequestMapping("/projects/{projectId}/tasks")
@Slf4j
public class TaskController {

    private final TaskRepository taskRepository;
    private final AppService service;

    @Autowired
    public TaskController(TaskRepository taskRepository, AppService service) {
        this.taskRepository = taskRepository;
        this.service = service;
    }

    @GetMapping
    @JsonView(TaskViews.CommonTask.class)
    public List<Task> findTasks(@PathVariable("projectId") int id, HttpServletRequest request){
        Util.printLog(request);
        return taskRepository.findTasksByProjectId(id);
    }

    @GetMapping("/filter")
    @JsonView(TaskViews.CommonTask.class)
    public List<Task> findFilteredTasks(@PathVariable("projectId") int projectId, HttpServletRequest request){
        Util.printLog(request);
        int developerId = Util.getCustomPrincipal(request).getId();
        return taskRepository.findFilteredTasks(projectId, developerId);
    }

    @PostMapping
    @JsonView(TaskViews.CommonTask.class)
    public ResponseEntity<?> save(@RequestBody Task task, @PathVariable("projectId") int projectId, HttpServletRequest request){
        Util.printLog(request);
        Objects.requireNonNull(task);
        return Objects.nonNull(taskRepository.findTaskByName(task.getName())) ?
                new ResponseEntity(HttpStatus.CONFLICT) :
                new ResponseEntity<>(service.saveTask(task, projectId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        taskRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(TaskViews.ExtendedTask.class)
    public ResponseEntity<?> findWithDetails(@PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        Task task = taskRepository.findTaskWithCommentsAndDeveloper(id);
        return Objects.nonNull(task) ?
                new ResponseEntity<>(task, HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@RequestBody TaskStatus status, @PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        Objects.requireNonNull(status);
        taskRepository.updateTaskStatus(status, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{id}/developer")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> setDeveloper(@RequestBody int developerId, @PathVariable("id") int taskId, HttpServletRequest request){
        Util.printLog(request);
        return service.setDeveloperToTask(developerId, taskId) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}

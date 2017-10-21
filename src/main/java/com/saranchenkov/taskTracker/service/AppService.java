package com.saranchenkov.taskTracker.service;

import com.saranchenkov.taskTracker.domain.Comment;
import com.saranchenkov.taskTracker.domain.Project;
import com.saranchenkov.taskTracker.domain.Task;
import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.repository.CommentRepository;
import com.saranchenkov.taskTracker.repository.ProjectRepository;
import com.saranchenkov.taskTracker.repository.TaskRepository;
import com.saranchenkov.taskTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Created by Ivan on 16.10.2017.
 */
@Service
public class AppService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AppService(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    // TODO: 18.10.2017 добавить к Project поле User и сохранять проект добавляя к нему reference на юзера
    @Transactional
    public Project saveProject(String name){
        Assert.hasLength(name, "Name of saving project is empty !");
        Project newProject = new Project(name);
        User manager = userRepository.getOne(1);
        newProject.getUsers().add(manager);
        return projectRepository.save(newProject);
    }
//    @Transactional
//    public Project saveProject(String name){
//        Assert.hasLength(name, "Name of saving project is empty !");
//        Project newProject = projectRepository.save(new Project(name));
//        User user = userRepository.findOne(1);
//        user.getProjects().add(newProject);
//        userRepository.save(user);
//        return newProject;
//    }

    @Transactional
    public boolean addDeveloperToProject(int projectId, int developerId){
        Project project = projectRepository.findOne(projectId);
        if(Objects.nonNull(project)){
            User developer = userRepository.getOne(developerId);
            project.getUsers().add(developer);
            projectRepository.save(project);
            return true;
        }
        return false;
    }

    @Transactional
    public Task saveTask(Task task, int projectId){
        Assert.notNull(task, "Saving task is null !");
        task.setProject(projectRepository.getOne(projectId));
        return taskRepository.save(task);
    }

    @Transactional
    public Comment saveComment(Comment comment, int taskId){
        Assert.notNull(comment, "Saving comment is null !");
        comment.setTask(taskRepository.getOne(taskId));
        return commentRepository.save(comment);
    }

    @Transactional
    public boolean updateComment(String content, int commentId){
        Objects.requireNonNull(content);
        if (Objects.nonNull(commentRepository.findOne(commentId))){
            commentRepository.updateCommentContent(content, commentId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean setDeveloperToTask(int developerId, int taskId){
        User developer = userRepository.getOne(developerId);
        Task task = taskRepository.findOne(taskId);
        if(Objects.nonNull(task)){
            task.setDeveloper(developer);
            return true;
        }
        return false;
    }
}

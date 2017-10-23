package com.saranchenkov.taskTracker.controller;

import com.saranchenkov.taskTracker.domain.Comment;
import com.saranchenkov.taskTracker.repository.CommentRepository;
import com.saranchenkov.taskTracker.service.AppService;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by Ivan on 18.10.2017.
 */

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@Slf4j
public class CommentController {

    private final CommentRepository commentRepository;
    private final AppService service;

    @Autowired
    public CommentController(CommentRepository commentRepository, AppService service) {
        this.commentRepository = commentRepository;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity saveComment(@RequestBody Comment comment, @PathVariable("taskId") int taskId, HttpServletRequest request){
        Util.printLog(request);
        Objects.requireNonNull(comment);
        return new ResponseEntity<>(service.saveComment(comment, taskId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        commentRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{id}/content")
    public ResponseEntity<?> updateComment(@RequestBody String content, @PathVariable("id") int id, HttpServletRequest request){
        Util.printLog(request);
        return service.updateComment(content, id) ?
                new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}

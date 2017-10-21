package com.saranchenkov.taskTracker.repository;

import com.saranchenkov.taskTracker.domain.Comment;
import com.saranchenkov.taskTracker.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by Ivan on 18.10.2017.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Transactional
    @Query("update Comment c set c.content = :content where c.id = :id")
    int updateCommentContent(@Param("content") String content, @Param("id") int id);

}

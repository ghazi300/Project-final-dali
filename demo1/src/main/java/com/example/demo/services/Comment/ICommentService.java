package com.example.demo.services.Comment;

import com.example.demo.entity.Comment;
import org.springframework.http.ResponseEntity;

public interface ICommentService {
    ResponseEntity<?> addCommentToProject(Integer idProject, Comment comment);

    void deleteComment(Integer commentId);
}
